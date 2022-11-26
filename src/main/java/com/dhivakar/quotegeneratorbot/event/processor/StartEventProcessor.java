package com.dhivakar.quotegeneratorbot.event.processor;


import com.dhivakar.quotegeneratorbot.data.QuoteBotAdapter;
import com.dhivakar.quotegeneratorbot.data.model.UserType;
import com.dhivakar.quotegeneratorbot.event.model.StartEvent;
import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.dhivakar.quotegeneratorbot.event.utils.BotUtil.NEXT_LINE;

@AllArgsConstructor
@Component
@Slf4j
public class StartEventProcessor {

    private final QuoteBotAdapter quoteBotAdapter;

    @EventListener(StartEvent.class)
    public SendMessage processEvent(StartEvent event) {

        Update update = event.getUpdate();
        String chatID = event.getChatID();
        User user = update.getMessage().getFrom();

        log.info("Register : New User - {} , Chat_ID - {}", user.getFirstName(), chatID);

        UserType userType = quoteBotAdapter.addUser(user, chatID);

        return sendWelcomeMessage(chatID, user, userType);

    }

    private SendMessage sendWelcomeMessage(String chatId, User user, UserType userType) {


        if (userType.equals(UserType.NEW)) {
            SendMessage sendingMessage = new SendMessage();
            sendingMessage.setChatId(chatId);
            StringBuilder s = new StringBuilder("Hi ");
            s.append(user.getFirstName());
            if (user.getLastName() != null) {
                s.append(user.getLastName());
            }
            s.append(NEXT_LINE);
            s.append(BotUtil.START_MSG);
            sendingMessage.setText(s.toString());
            return sendingMessage;
        } else {

            SendMessage sendingMessage = new SendMessage();
            sendingMessage.setChatId(chatId);
            StringBuilder s = new StringBuilder("Welcome Back ");
            s.append(user.getFirstName());
            if (user.getLastName() != null) {
                s.append(user.getLastName());
            }
            s.append(NEXT_LINE);
            s.append(BotUtil.WELCOME_BACK);

            sendingMessage.setText(s.toString());
            return sendingMessage;
        }
    }


}
