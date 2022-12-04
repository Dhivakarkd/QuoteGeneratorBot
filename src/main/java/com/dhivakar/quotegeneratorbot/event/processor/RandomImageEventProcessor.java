package com.dhivakar.quotegeneratorbot.event.processor;

import com.dhivakar.quotegeneratorbot.event.model.RandomImageEvent;
import com.dhivakar.quotegeneratorbot.rest.client.QuoteGeneratorClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.InputStream;

@AllArgsConstructor
@Component
@Slf4j
public class RandomImageEventProcessor {

    private final QuoteGeneratorClient quotesClient;

    @EventListener(RandomImageEvent.class)
    public SendPhoto processEvent(RandomImageEvent event) {

        log.info("RandomImage : Chat_ID - {}", event.getChatID());
        InputStream q = quotesClient.getFile();

        return event.defaultMessage(q);

    }
}
