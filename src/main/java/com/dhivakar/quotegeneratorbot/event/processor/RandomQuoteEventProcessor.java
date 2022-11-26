package com.dhivakar.quotegeneratorbot.event.processor;

import com.dhivakar.quotegeneratorbot.event.model.RandomQuoteEvent;
import com.dhivakar.quotegeneratorbot.model.QuoteVO;
import com.dhivakar.quotegeneratorbot.rest.client.QuoteGeneratorClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@AllArgsConstructor
@Component
@Slf4j
public class RandomQuoteEventProcessor {

    private final QuoteGeneratorClient quotesClient;

    @EventListener(RandomQuoteEvent.class)
    public SendMessage processEvent(RandomQuoteEvent event) {

        log.info("RandomQuote : Chat_ID - {}", event.getChatID());
        QuoteVO q = quotesClient.generateQuote();

        return event.defaultMessage(q.formattedQuote());

    }
}
