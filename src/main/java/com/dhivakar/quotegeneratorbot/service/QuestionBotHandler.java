package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.data.adapter.QuestionAdapter;
import com.dhivakar.quotegeneratorbot.helper.QuestionHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class QuestionBotHandler extends TelegramLongPollingBot {
    public static final String PUBLISHED_EVENT_LOG = "Published {} event for Command {}";
    private static final String BOT_NAME = System.getenv("QUES_BOT_NAME");
    private static final String BOT_TOKEN = System.getenv("QUES_BOT_TOKEN");
    @Autowired
    private QuestionAdapter adapter;

    @Autowired
    private QuestionHelper questionHelper;

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

            String command = update.getMessage().getText();
            if (questionHelper.isStartCommand(command)) {
                log.info(PUBLISHED_EVENT_LOG, "START", command);
                publishMessage(questionHelper.generateSendMessage(update));
            } else if (questionHelper.isQuesCommand(command)) {
                log.info(PUBLISHED_EVENT_LOG, "Question", command);
                publishMessage(questionHelper.generateQuestionMessage(update));
            } else if (questionHelper.isNHIECommand(command)) {
                log.info(PUBLISHED_EVENT_LOG, "NHIE", command);
                publishMessage(questionHelper.generateNHIEMessage(update.getMessage().getChatId()));
            } else if (questionHelper.isSillyCommand(command)) {
                log.info(PUBLISHED_EVENT_LOG, "SILLY", command);
                publishMessage(questionHelper.generateSillyMessage(update.getMessage().getChatId()));
            } else if (questionHelper.isDeepQuestion(command)) {
                log.info(PUBLISHED_EVENT_LOG, "DEEP", command);
                publishMessage(questionHelper.generateDeepMessage(update.getMessage().getChatId()));
            } else if (questionHelper.isTODQuestion(command)) {
                log.info(PUBLISHED_EVENT_LOG, "TOD", command);
                publishMessage(questionHelper.generateTruthOrDareMessage(update));
            } else {
                log.info("Received a Command : {}", command);
            }
        } else if (update.hasCallbackQuery()) {

            EditMessageText editMessageText = questionHelper.handleCallBack(update);
            publishMessage(editMessageText);

        } else {
            log.info("Message is {}", update.getMessage().getText());
        }
    }

    private void publishMessage(EditMessageText editMessageText) {
        try {
            log.info("Sent Edit Message");
            execute(editMessageText);
        } catch (TelegramApiException e) {
            log.error("Error When sending Question Update to Telegram", e);
        }
    }

    private void publishMessage(SendMessage m) {
        try {
            log.info("Send Message to User");
            execute(m);
        } catch (TelegramApiException e) {
            log.error("Error When sending Question to Telegram", e);
        }
    }
}
