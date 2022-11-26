package com.dhivakar.quotegeneratorbot.data;

import com.dhivakar.quotegeneratorbot.data.model.BotUser;
import com.dhivakar.quotegeneratorbot.data.model.UserStatus;
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

    public void addUser(User user, long chatId) {

        BotUser botUser = BotUser.builder()
                .chatID(chatId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(UserStatus.ACTIVE)
                .build();

        quoteBotUserRepo.save(botUser);

        log.info("User ID : {} Saved to DB Successfully", chatId);

    }

    public List<BotUser> getAllActiveUserList() {

        return quoteBotUserRepo.findByStatus(UserStatus.ACTIVE);

    }

    public boolean disableActiveUser(long chatId) {

        Optional<BotUser> user = quoteBotUserRepo.findByChatId(chatId);

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
