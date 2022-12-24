package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.helper.QuestionHelper;
import com.dhivakar.quotegeneratorbot.model.QuestionCommand;
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
            processMessage(update, command);
        } else if (update.hasCallbackQuery()) {

            EditMessageText editMessageText = questionHelper.handleCallBack(update);
            publishMessage(editMessageText);

        } else {
            log.info("Message is {}", update.getMessage().getText());
        }
    }

    private void processMessage(Update update, String userCommand) {

        QuestionCommand commandEnum = QuestionCommand.getAPIEnumFromValue(userCommand);

        switch (commandEnum) {

            case START:
                log.info(PUBLISHED_EVENT_LOG, commandEnum, commandEnum.getCommand());
                publishMessage(questionHelper.generateSendMessage(update));
                break;
            case QUESTION:
                log.info(PUBLISHED_EVENT_LOG, commandEnum, commandEnum.getCommand());
                publishMessage(questionHelper.generateQuestionMessage(update));
                break;
            case TRUTH_OR_DARE:
                log.info(PUBLISHED_EVENT_LOG, commandEnum, commandEnum.getCommand());
                publishMessage(questionHelper.generateTruthOrDareMessage(update));
                break;
            case FUNNY:
            case WEIRD:
            case DEEP_QUESTION:
            case DONT_ASK_IT:
            case TRUTH:
            case DARE:
            case NEVER_HAVE_I_EVER:
                log.info(PUBLISHED_EVENT_LOG, commandEnum, commandEnum.getCommand());
                publishMessage(questionHelper.generateDefaultMessage(commandEnum, update.getMessage().getChatId()));
                break;
            case DEFAULT:

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
