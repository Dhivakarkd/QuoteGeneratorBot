package com.dhivakar.quotegeneratorbot.event.model;

import com.dhivakar.quotegeneratorbot.model.QuoteCommand;
import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RandomQuoteEvent extends BaseEvent {

    @Builder
    public RandomQuoteEvent(String chatID, Update update, QuoteCommand quoteCommand) {
        super(chatID, update, quoteCommand);
    }

    public SendMessage defaultMessage(String messageText) {
        return super.defaultMessage(messageText);
    }
}
