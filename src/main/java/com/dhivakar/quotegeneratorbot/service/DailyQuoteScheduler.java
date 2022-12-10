package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.data.adapter.QuoteBotAdapter;
import com.dhivakar.quotegeneratorbot.data.model.BotUser;
import com.dhivakar.quotegeneratorbot.rest.client.QuoteGeneratorClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@Slf4j
public class DailyQuoteScheduler {


    private final QuoteGeneratorClient quotesClient;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final QuoteBotAdapter botAdapter;

    @Autowired
    public DailyQuoteScheduler(QuoteGeneratorClient quotesClient, ApplicationEventPublisher applicationEventPublisher, QuoteBotAdapter botAdapter) {
        this.quotesClient = quotesClient;
        this.applicationEventPublisher = applicationEventPublisher;
        this.botAdapter = botAdapter;
    }

    @Scheduled(cron = "0 0 12,18 * * ?")
    public void sendDailyQuoteToActiveBotUsers() {

        List<BotUser> botUserList = botAdapter.getAllActiveUserList();

        if (!botUserList.isEmpty()) {
            log.info("Sending Daily Quote to {} Active Users", botUserList.size());

            for (BotUser u : botUserList) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(u.getChatID()));
                message.setText(quotesClient.generateQuote().formattedQuote());

                applicationEventPublisher.publishEvent(message);
            }
        } else {
            log.warn("No Active User exists in database");
        }


    }
}
