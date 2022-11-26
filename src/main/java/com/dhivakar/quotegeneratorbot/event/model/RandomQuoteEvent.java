package com.dhivakar.quotegeneratorbot.event.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import com.dhivakar.quotegeneratorbot.model.UserCommand;
import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RandomQuoteEvent extends BaseEvent{

    @Builder
    public RandomQuoteEvent(String chatID, Update update, UserCommand userCommand) {
        super(chatID, update, userCommand);
    }

    public SendMessage defaultMessage(String messageText) {
        return super.defaultMessage(messageText);
    }
}
