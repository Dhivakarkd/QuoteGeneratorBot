package com.dhivakar.quotegeneratorbot.event.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import com.dhivakar.quotegeneratorbot.model.UserCommand;
import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dhivakar.quotegeneratorbot.event.utils.BotUtil.NEXT_LINE;


public class StartEvent extends BaseEvent {

    @Builder
    public StartEvent(String chatID, Update update, UserCommand userCommand) {
        super(chatID, update, userCommand);
    }

    public SendMessage defaultMessage() {
        String msg = "Hi !!!" + NEXT_LINE +
                BotUtil.START_MSG;
        return super.defaultMessage(msg);
    }

}
