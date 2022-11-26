package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.data.QuoteBotAdapter;
import com.dhivakar.quotegeneratorbot.event.model.DefaultEvent;
import com.dhivakar.quotegeneratorbot.event.model.DisableUserEvent;
import com.dhivakar.quotegeneratorbot.event.model.RandomQuoteEvent;
import com.dhivakar.quotegeneratorbot.event.model.StartEvent;
import com.dhivakar.quotegeneratorbot.model.UserCommand;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class QuoteBotHandler extends TelegramLongPollingBot {

    public static final String PUBLISHED_EVENT_LOG = "Published {} event";
    private static final String BOT_NAME = System.getenv("BOT_NAME");
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private QuoteBotAdapter botAdapter;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        processMessage(update);
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @EventListener(SendMessage.class)
    public void sendMessageToUsers(SendMessage message) {
        log.info("Message sent");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error When sending Message to Telegram", e);
        }
    }

    public void processMessage(Update update) {

        if (update.hasMessage() && update.getMessage().getText().equalsIgnoreCase(UserCommand.START.getCommand())) {

            String chatId = String.valueOf(update.getMessage().getChat().getId());
            StartEvent s = StartEvent.builder().chatID(chatId).userCommand(UserCommand.START).update(update).build();
            log.info(PUBLISHED_EVENT_LOG, UserCommand.START);
            publisher.publishEvent(s);

        } else if (update.hasMessage() && update.getMessage().getText().equalsIgnoreCase(UserCommand.RANDOM_QUOTE.getCommand())) {


            String chatId = String.valueOf(update.getMessage().getChat().getId());
            RandomQuoteEvent randomQuoteEvent = RandomQuoteEvent.builder().chatID(chatId).userCommand(UserCommand.RANDOM_QUOTE).update(update).build();
            log.info(PUBLISHED_EVENT_LOG, UserCommand.RANDOM_QUOTE);
            publisher.publishEvent(randomQuoteEvent);


        } else if (update.hasMessage() && update.getMessage().getText().equalsIgnoreCase(UserCommand.DISABLE_QUOTE_SCHEDULING.getCommand())) {

            String chatId = String.valueOf(update.getMessage().getChat().getId());
            DisableUserEvent disableUserEvent = DisableUserEvent.builder().chatID(chatId).userCommand(UserCommand.DISABLE_QUOTE_SCHEDULING).update(update).build();
            log.info(PUBLISHED_EVENT_LOG, UserCommand.DISABLE_QUOTE_SCHEDULING);
            publisher.publishEvent(disableUserEvent);

        } else {

            String chatId = String.valueOf(update.getMessage().getChat().getId());
            DefaultEvent defaultEvent = DefaultEvent.builder().chatID(chatId).userCommand(UserCommand.DEFAULT).update(update).build();
            log.info(PUBLISHED_EVENT_LOG, UserCommand.DEFAULT);
            publisher.publishEvent(defaultEvent);
        }


    }

}