package com.dhivakar.quotegeneratorbot.data.adapter;

import com.dhivakar.quotegeneratorbot.data.model.BotUser;
import com.dhivakar.quotegeneratorbot.data.model.UserStatus;
import com.dhivakar.quotegeneratorbot.data.model.UserType;
import com.dhivakar.quotegeneratorbot.data.repo.QuoteBotUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class QuoteBotAdapter {

    private final QuoteBotUserRepo quoteBotUserRepo;

    @Autowired
    public QuoteBotAdapter(QuoteBotUserRepo quoteBotUserRepo) {
        this.quoteBotUserRepo = quoteBotUserRepo;
    }

    public UserType addUser(User latestUser, String chatId) {

        Optional<BotUser> userFromDB = quoteBotUserRepo.findByChatID(chatId);

        if (userFromDB.isPresent()) {
            BotUser dbUser = userFromDB.get();
            dbUser.setStatus(UserStatus.ACTIVE);
            quoteBotUserRepo.save(dbUser);
            log.info("Existing User ID : {} updated to Active Status", chatId);

            return UserType.ALREADY_EXISTS;
        } else {

            BotUser botUser = BotUser.builder()
                    .chatID(chatId)
                    .firstName(latestUser.getFirstName())
                    .lastName(latestUser.getLastName())
                    .status(UserStatus.ACTIVE)
                    .build();

            quoteBotUserRepo.save(botUser);

            log.info("New User ID : {} Saved to DB ", chatId);
            return UserType.NEW;
        }
    }

    public List<BotUser> getAllActiveUserList() {

        return quoteBotUserRepo.findByStatus(UserStatus.ACTIVE);

    }

    public boolean disableActiveUser(String chatId) {

        Optional<BotUser> user = quoteBotUserRepo.findByChatID(chatId);

        if (user.isPresent() && user.get().getStatus() == UserStatus.ACTIVE) {
            BotUser userFromDb = user.get();

            userFromDb.setStatus(UserStatus.INACTIVE);

            quoteBotUserRepo.save(userFromDb);

            log.info("User ID : {} Disabled for Scheduling ", chatId);

            return true;
        }
        return false;

    }
}
