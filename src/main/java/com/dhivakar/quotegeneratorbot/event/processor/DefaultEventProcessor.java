package com.dhivakar.quotegeneratorbot.event.processor;

import com.dhivakar.quotegeneratorbot.event.model.DefaultEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class DefaultEventProcessor {

    @EventListener(DefaultEvent.class)
    public SendMessage processEvent(DefaultEvent event) {

        return event.defaultMessage();
    }
}
