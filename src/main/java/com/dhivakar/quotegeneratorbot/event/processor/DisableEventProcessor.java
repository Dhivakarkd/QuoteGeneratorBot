package com.dhivakar.quotegeneratorbot.event.processor;

import com.dhivakar.quotegeneratorbot.data.adapter.QuoteBotAdapter;
import com.dhivakar.quotegeneratorbot.event.model.DisableUserEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@AllArgsConstructor
@Component
@Slf4j
public class DisableEventProcessor {

    private final QuoteBotAdapter quoteBotAdapter;

    @EventListener(DisableUserEvent.class)
    public SendMessage processEvent(DisableUserEvent event) {
        String chatID = event.getChatID();

        if (quoteBotAdapter.disableActiveUser(chatID)) {

            return event.defaultMessage();
        } else {

            return event.defaultErrorMessage();
        }
    }
}
