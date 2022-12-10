package com.dhivakar.quotegeneratorbot.event.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import com.dhivakar.quotegeneratorbot.model.QuoteCommand;
import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DisableUserEvent extends BaseEvent {

    @Builder
    public DisableUserEvent(String chatID, Update update, QuoteCommand quoteCommand) {
        super(chatID, update, quoteCommand);
    }

    public SendMessage defaultMessage() {
        return super.defaultMessage(BotUtil.DISABLE_MSG);
    }
}
