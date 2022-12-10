package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.data.adapter.QuoteBotAdapter;
import com.dhivakar.quotegeneratorbot.event.model.*;
import com.dhivakar.quotegeneratorbot.model.QuoteCommand;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class QuoteBotHandler extends TelegramLongPollingBot {

    public static final String PUBLISHED_EVENT_LOG = "Published {} event for Command {}";
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

    @EventListener(SendPhoto.class)
    public void sendMessageToUsers(SendPhoto message) {
        log.info("Photo sent");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error When sending photo to Telegram", e);
        }
    }

    public void processMessage(Update update) {

        if (update.hasMessage() && update.getMessage().isCommand()) {

            String command = update.getMessage().getText();

            QuoteCommand quoteCommand = QuoteCommand.getAPIEnumFromValue(command);

            String chatId;
            switch (quoteCommand) {

                case START:
                    chatId = String.valueOf(update.getMessage().getChat().getId());
                    StartEvent s = StartEvent.builder().chatID(chatId).quoteCommand(QuoteCommand.START).update(update).build();
                    log.info(PUBLISHED_EVENT_LOG, QuoteCommand.START, command);
                    publisher.publishEvent(s);
                    break;

                case RANDOM_QUOTE:
                    chatId = String.valueOf(update.getMessage().getChat().getId());
                    RandomQuoteEvent randomQuoteEvent = RandomQuoteEvent.builder().chatID(chatId).quoteCommand(QuoteCommand.RANDOM_QUOTE).update(update).build();
                    log.info(PUBLISHED_EVENT_LOG, QuoteCommand.RANDOM_QUOTE, command);
                    publisher.publishEvent(randomQuoteEvent);
                    break;

                case RANDOM_IMAGE:
                    chatId = String.valueOf(update.getMessage().getChat().getId());
                    RandomImageEvent randomImageEvent = RandomImageEvent.builder().chatID(chatId).quoteCommand(QuoteCommand.RANDOM_QUOTE).update(update).build();
                    log.info(PUBLISHED_EVENT_LOG, QuoteCommand.RANDOM_IMAGE, command);
                    publisher.publishEvent(randomImageEvent);
                    break;

                case DISABLE_QUOTE_SCHEDULING:
                    chatId = String.valueOf(update.getMessage().getChat().getId());
                    DisableUserEvent disableUserEvent = DisableUserEvent.builder().chatID(chatId).quoteCommand(QuoteCommand.DISABLE_QUOTE_SCHEDULING).update(update).build();
                    log.info(PUBLISHED_EVENT_LOG, QuoteCommand.DISABLE_QUOTE_SCHEDULING, command);
                    publisher.publishEvent(disableUserEvent);
                    break;

                case DEFAULT:
                    chatId = String.valueOf(update.getMessage().getChat().getId());
                    DefaultEvent defaultEvent = DefaultEvent.builder().chatID(chatId).quoteCommand(QuoteCommand.DEFAULT).update(update).build();
                    log.info(PUBLISHED_EVENT_LOG, QuoteCommand.DEFAULT, command);
                    publisher.publishEvent(defaultEvent);
                    break;

                default:
                    log.warn("Received Text Other than Command : {} ", update.getMessage().getText());

            }


        }

    }

}