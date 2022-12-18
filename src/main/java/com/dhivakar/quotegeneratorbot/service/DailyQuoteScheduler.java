package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.data.adapter.QuoteBotAdapter;
import com.dhivakar.quotegeneratorbot.data.model.BotUser;
import com.dhivakar.quotegeneratorbot.event.model.RandomImageEvent;
import com.dhivakar.quotegeneratorbot.model.QuoteCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DailyQuoteScheduler {


    private final ApplicationEventPublisher applicationEventPublisher;
    private final QuoteBotAdapter botAdapter;

    @Autowired
    public DailyQuoteScheduler(ApplicationEventPublisher applicationEventPublisher, QuoteBotAdapter botAdapter) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.botAdapter = botAdapter;
    }

    @Scheduled(cron = "0 0 0,23 * * ?")
    public void sendDailyQuoteToActiveBotUsers() {

        List<BotUser> botUserList = botAdapter.getAllActiveUserList();

        if (!botUserList.isEmpty()) {
            log.info("Sending Daily Quote to {} Active Users", botUserList.size());

            botUserList.forEach(u -> applicationEventPublisher.publishEvent(generatePhotoMessage(u.getChatID())));

        } else {
            log.warn("No Active User exists in database");
        }

    }

    private RandomImageEvent generatePhotoMessage(String chatID) {

        return RandomImageEvent
                .builder()
                .quoteCommand(QuoteCommand.RANDOM_IMAGE)
                .chatID(chatID)
                .build();

    }
}
