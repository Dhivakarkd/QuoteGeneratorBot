package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.data.adapter.QuestionAdapter;
import com.dhivakar.quotegeneratorbot.data.model.QuestionDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class QuestionBotHandler extends TelegramLongPollingBot {
    private static final String BOT_NAME = System.getenv("QUES_BOT_NAME");
    private static final String BOT_TOKEN = System.getenv("QUES_BOT_TOKEN");

    @Autowired
    private QuestionAdapter adapter;

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().isCommand()) {

            SendMessage m = new SendMessage();
            m.setText("Select Question Category");
            m.setChatId(update.getMessage().getChatId());
            m.setReplyMarkup(getInlineKeyboardMarkup());

            publishMessage(m);
        } else if (update.hasCallbackQuery()) {

            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.equals("funny_ques")) {
                String answer = "Updated message text";

                QuestionDO questionDO = adapter.getQuestion();

                EditMessageText editMessageText = EditMessageText.builder()
                        .chatId(chat_id)
                        .messageId(Math.toIntExact(message_id))
                        .text(questionDO.getQuestion())
                        .build();

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void publishMessage(SendMessage m) {
        try {
            execute(m);
        } catch (TelegramApiException e) {
            log.error("Error When sending photo to Telegram", e);
        }
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("Funny");
        row.add("Weird");


        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup() {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton funnyButton = new InlineKeyboardButton();

        funnyButton.setText("Funny");
        funnyButton.setCallbackData("funny_ques");

        InlineKeyboardButton weirdButton = new InlineKeyboardButton();

        weirdButton.setText("Weird");
        weirdButton.setCallbackData("weird_ques");

        rowInline.add(funnyButton);
        rowInline.add(weirdButton);

        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }
}
