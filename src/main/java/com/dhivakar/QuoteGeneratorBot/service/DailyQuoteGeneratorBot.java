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
    private final String botName = System.getenv("BOT_NAME");
    private final String bottoken = System.getenv("BOT_TOKEN");
    public long chat_ID;
    QuoteGeneratorService generatorService = new QuoteGeneratorService();
    Quote q = new Quote();

    public DailyQuoteGeneratorBot() {

    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        processMessage(update);
    }

    @Scheduled(cron = "0 0 12,18 * * ?")
    public void sendquote() {

        if (!botUserList.isEmpty()) {
            System.out.println("Scheduled Method Called");
            System.out.println("List Size is " + botUserList.size());
            q = generatorService.generateQuote();
            String s = q.getQuote() + "\n\n\t\t- " + q.getAuthor();
            System.out.println(q.toString());
            for (BotUser u : botUserList) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(u.getChatID()));
                message.setText(s);
                System.out.println(message);
                finalizemessage(message);


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

    public void processMessage(Update update) {

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            chat_ID = update.getMessage().getChat().getId();
            User user = update.getMessage().getFrom();
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