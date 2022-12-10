package com.dhivakar.quotegeneratorbot.event.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import com.dhivakar.quotegeneratorbot.model.QuoteCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@AllArgsConstructor
public class BaseEvent {

    private String chatID;
    private Update update;
    private QuoteCommand quoteCommand;

    protected SendMessage defaultMessage(String messageText) {
        SendMessage sendingMessage = new SendMessage();
        sendingMessage.setChatId(getChatID());
        sendingMessage.setText(messageText);
        return sendingMessage;
    }

    public SendMessage defaultErrorMessage() {
        return BotUtil.errorMessage(chatID);
    }
}
