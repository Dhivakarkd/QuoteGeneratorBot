package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.model.BotUser;
import com.dhivakar.quotegeneratorbot.model.Quote;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DailyQuoteGeneratorBot extends TelegramLongPollingBot {


    static List<BotUser> botUserList = new ArrayList<>();
    private final String botName = System.getenv("BOT_NAME");
    private final String bottoken = System.getenv("BOT_TOKEN");
    public long chat_ID;
    QuoteGeneratorService generatorService = new QuoteGeneratorService();
    Quote q = new Quote();

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        processMessage(update);
    }

    @Scheduled(cron = "0 0 12,18 * * ?")
    public void sendquote() {

        if (!botUserList.isEmpty()) {
            log.info("Scheduled Method Called");
            log.info("List Size is " + botUserList.size());
            q = generatorService.generateQuote();
            String s = q.getQuote() + "\n\n\t\t- " + q.getAuthor();
            log.info(q.toString());
            for (BotUser u : botUserList) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(u.getChatID()));
                message.setText(s);
                log.debug(message.toString());
                finalizemessage(message);


            }
        } else {
            log.info("User List is Empty");
        }


    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return bottoken;
    }

    public void finalizemessage(SendMessage message) {
        log.info("Message sent");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void processMessage(Update update) {

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {

            chat_ID = update.getMessage().getChat().getId();
            User user = update.getMessage().getFrom();
            log.info("Register : New User - {} , Chat_ID - {}",user.getLastName(),chat_ID);
            BotUser botlist = new BotUser();
            botlist.setChatID(chat_ID);
            if (!botUserList.contains(botlist)) {
                botUserList.add(botlist);
            }
            SendMessage sendingMessage = new SendMessage();
            sendingMessage.setChatId(String.valueOf(chat_ID));
            StringBuilder s = new StringBuilder("Hi ");
            s.append(user.getFirstName());
            if (user.getLastName() != null) {
                s.append(user.getLastName());
            }
            s.append("\n\n");
            s.append("Your Account has been Registered Successfully\n\n");
            s.append("To get Random Quotes send /randomquote");
            sendingMessage.setText(s.toString());
            finalizemessage(sendingMessage);

        } else if (update.hasMessage() && update.getMessage().getText().equals("/randomquote")) {

            log.info("RandomQuote : Chat_ID - {}",chat_ID);
            chat_ID = update.getMessage().getChat().getId();
            q = generatorService.generateQuote();
            String s = q.getQuote() + "\n\n- " + q.getAuthor();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chat_ID));
            message.setText(s);
            finalizemessage(message);


        }


    }
}