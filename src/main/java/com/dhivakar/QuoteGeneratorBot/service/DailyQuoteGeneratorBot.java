package com.dhivakar.QuoteGeneratorBot.service;

import com.dhivakar.QuoteGeneratorBot.model.BotUser;
import com.dhivakar.QuoteGeneratorBot.model.Quote;
import lombok.SneakyThrows;
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
public class DailyQuoteGeneratorBot extends TelegramLongPollingBot {



    static List<BotUser> botUserList = new ArrayList<>();
    private final String botName = "DailyQuoteGeneratorBot";
            //System.getenv("BOT_NAME");
    private final String bottoken ="1759075206:AAHdI9gsGJlH0uvrEwUw78PmUhp5pL-bBCc";
                    //System.getenv("BOT_TOKEN");
    public long chat_ID;
    QuoteGeneratorService generatorService = new QuoteGeneratorService();
    Quote q = new Quote();

    public DailyQuoteGeneratorBot() {
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            //System.out.println(update);
            chat_ID = update.getMessage().getChat().getId();
            User user = update.getMessage().getFrom();
            BotUser botlist = new BotUser();
            botlist.setChatID(chat_ID);
            if (!botUserList.contains(botlist)) {
                botUserList.add(botlist);
            }
            SendMessage sendingMessage = new SendMessage();
            sendingMessage.setChatId(String.valueOf(chat_ID));
            sendingMessage.setText("Hi " + user.getFirstName() + " " + user.getLastName());
            finalizemessage(sendingMessage);

        }
        for (BotUser u : botUserList) {
            System.out.println(u);
        }

    }

    @Scheduled(cron = "0 12,18 * * ?")
    public void sendquote() {

        if (!botUserList.isEmpty()) {
            System.out.println("Scheduled Method Called");
            System.out.println("List Size is " + botUserList.size());
            q = generatorService.generateQuote();
            String s = q.getQuote() + "\n              - " + q.getAuthor();
            System.out.println(q.toString());
            for (BotUser u : botUserList) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(u.getChatID()));
                message.setText(s);
                System.out.println(message);
                try {
                    execute(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            System.out.println("User List is Empty");
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
        System.out.println("Message sent");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}