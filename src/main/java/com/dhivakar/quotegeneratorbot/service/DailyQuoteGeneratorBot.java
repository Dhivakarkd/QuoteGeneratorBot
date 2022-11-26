package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.data.QuoteBotAdapter;
import com.dhivakar.quotegeneratorbot.data.model.BotUser;
import com.dhivakar.quotegeneratorbot.model.Quote;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String BOT_NAME = System.getenv("BOT_NAME");
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    static List<BotUser> botUserList = new ArrayList<>();
    public long chat_ID;
    @Autowired
    QuoteGeneratorService generatorService;
    Quote q = new Quote();
    @Autowired
    private QuoteBotAdapter botAdapter;

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
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
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
            log.info("Register : New User - {} , Chat_ID - {}", user.getLastName(), chat_ID);
            BotUser botlist = new BotUser();
            botlist.setChatID(chat_ID);

            botAdapter.addUser(user,chat_ID);

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
            s.append("To Disable Receiving Daily Quotes send /disablequote");
            sendingMessage.setText(s.toString());
            finalizemessage(sendingMessage);

        } else if (update.hasMessage() && update.getMessage().getText().equals("/randomquote")) {


            chat_ID = update.getMessage().getChat().getId();
            log.info("RandomQuote : Chat_ID - {}", chat_ID);
            q = generatorService.generateQuote();
            String s = q.getQuote() + "\n\n- " + q.getAuthor();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chat_ID));
            message.setText(s);
            finalizemessage(message);


        } else if (update.hasMessage() && update.getMessage().getText().equals("/updateTable")) {

            log.info("Update Table Called");

            List<BotUser> userList = botAdapter.getAllActiveUserList();

            if (!userList.isEmpty()) {

                userList.forEach(i -> log.info("{} is the id", i.getId()));
            }

        } else if (update.hasMessage() && update.getMessage().getText().equals("/disableQuote")){

            chat_ID = update.getMessage().getChat().getId();

            SendMessage sendingMessage = new SendMessage();
            sendingMessage.setChatId(String.valueOf(chat_ID));
            StringBuilder s = new StringBuilder();
            s.append("Daily Quotes have been disabled Successfully\n\n");
            s.append("To Re-Enable send /start");
            s.append("To Disable Receiving Daily Quotes send /disableQuote");
            sendingMessage.setText(s.toString());
            finalizemessage(sendingMessage);


        }


    }
}