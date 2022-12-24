package com.dhivakar.quotegeneratorbot.helper;


import com.dhivakar.quotegeneratorbot.data.adapter.QuestionAdapter;
import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import com.dhivakar.quotegeneratorbot.model.QuestionCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class QuestionHelper {


    private final QuestionAdapter questionAdapter;

    public SendMessage generateSendMessage(Update update) {

        String lastName = update.getMessage().getFrom().getFirstName();

        String replyText = "Welcome " + lastName + "\uD83D\uDC4B" + BotUtil.NEXT_LINE +
                "If you are struggling to come up with questions to start/continue a conversation" + BotUtil.NEXT_LINE +
                "We have list of questions to help you" + "\uD83D\uDCAC" + BotUtil.NEXT_LINE +
                "Send " + QuestionCommand.QUESTION.getCommand() + " to get some random questions as per your request \uD83D\uDCE7" + BotUtil.NEXT_LINE +
                "Send " + QuestionCommand.NEVER_HAVE_I_EVER.getCommand() + " to get Never Have I Ever Questions \uD83E\uDD37\uD83C\uDFFB" + BotUtil.NEXT_LINE +
                "Send " + QuestionCommand.TRUTH_OR_DARE.getCommand() + " to get Truth or Dare Questions " + BotUtil.NEXT_LINE +
                "Send " + QuestionCommand.DEEP_QUESTION.getCommand() + " to Deep Meaningful Questions";


        return SendMessage.builder()
                .text(replyText)
                .chatId(update.getMessage().getChatId())
                .build();

    }


    public SendMessage generateQuestionMessage(Update update) {

        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Select Question Type ")
                .replyMarkup(getInlineKeyboardMarkup())
                .build();

    }

    public SendMessage generateTruthOrDareMessage(Update update) {

        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Select the Question Type")
                .replyMarkup(getTODInlineKeyboardMarkup())
                .build();

    }


    public SendMessage generateDefaultMessage(QuestionCommand command, long chatID) {

        String question = questionAdapter.getQuestion(command).getQuestion();

        return SendMessage.builder()
                .chatId(chatID)
                .text(question)
                .build();
    }


    public EditMessageText handleCallBack(Update update) {

        String callBackReply = update.getCallbackQuery().getData();

        String question = getQuestion(callBackReply);

        return generateEditMessage(update, question);

    }

    private String getQuestion(String callBackReply) {

        if (CommonUtil.DEEP_QUES_CALLBACK.equals(callBackReply)) {
            return questionAdapter.getDeepQuestion().getQuestion();
        } else if (CommonUtil.WEIRD_CALL_BACK.equals(callBackReply)) {
            return questionAdapter.getWeirdQuestion().getQuestion();
        } else if (CommonUtil.TRUTH_QUES_CALLBACK.equals(callBackReply)) {
            return questionAdapter.getTruthQuestion().getQuestion();
        } else if (CommonUtil.DARE_QUES_CALLBACK.equals(callBackReply)) {
            return questionAdapter.getDareQuestion().getQuestion();
        } else {
            return questionAdapter.getFunnyQuestion().getQuestion();
        }


    }

    private EditMessageText generateEditMessage(Update update, String question) {

        long messageID = update.getCallbackQuery().getMessage().getMessageId();
        long chatID = update.getCallbackQuery().getMessage().getChatId();

        return EditMessageText.builder()
                .chatId(chatID)
                .messageId(Math.toIntExact(messageID))
                .text(question)
                .build();

    }


    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(CommonUtil.FUNNY_TEXT);
        row.add(CommonUtil.WEIRD_TEXT);

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup() {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

        InlineKeyboardButton funnyButton = new InlineKeyboardButton();

        funnyButton.setText(CommonUtil.FUNNY_TEXT);
        funnyButton.setCallbackData(CommonUtil.FUNNY_CALL_BACK);

        InlineKeyboardButton weirdButton = new InlineKeyboardButton();

        weirdButton.setText(CommonUtil.WEIRD_TEXT);
        weirdButton.setCallbackData(CommonUtil.WEIRD_CALL_BACK);

        InlineKeyboardButton deepMeaningButton = new InlineKeyboardButton();

        deepMeaningButton.setText(CommonUtil.DEEP_TEXT);
        deepMeaningButton.setCallbackData(CommonUtil.DEEP_QUES_CALLBACK);

        rowInline1.add(funnyButton);
        rowInline1.add(weirdButton);
        rowInline2.add(deepMeaningButton);

        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    private InlineKeyboardMarkup getTODInlineKeyboardMarkup() {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

        InlineKeyboardButton truthButton = new InlineKeyboardButton();

        truthButton.setText(CommonUtil.TRUTH_TEXT);
        truthButton.setCallbackData(CommonUtil.TRUTH_QUES_CALLBACK);

        InlineKeyboardButton dareButton = new InlineKeyboardButton();

        dareButton.setText(CommonUtil.DARE_TEXT);
        dareButton.setCallbackData(CommonUtil.DARE_QUES_CALLBACK);


        rowInline1.add(truthButton);
        rowInline1.add(dareButton);

        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }


}
