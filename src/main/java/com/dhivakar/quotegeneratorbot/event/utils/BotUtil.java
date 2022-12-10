package com.dhivakar.quotegeneratorbot.event.utils;

import com.dhivakar.quotegeneratorbot.model.QuoteCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class BotUtil {


    public static final String NEXT_LINE = "\n\n";
    public static final String TAB_SPACE = "\t\t";
    public static final String GET_RANDOM_QUOTE_MSG = "To get Random Quotes send " + QuoteCommand.RANDOM_QUOTE.getCommand();
    public static final String GET_DISABLE_MSG = "To Disable Receiving Daily Quotes send " + QuoteCommand.DISABLE_QUOTE_SCHEDULING.getCommand();
    public static final String INIT_ACCT_SUCCESS = "Your Account has been Registered Successfully";
    public static final String START_MSG = INIT_ACCT_SUCCESS + NEXT_LINE + GET_RANDOM_QUOTE_MSG + NEXT_LINE + GET_DISABLE_MSG;
    public static final String WELCOME_BACK = GET_RANDOM_QUOTE_MSG + NEXT_LINE + GET_DISABLE_MSG;
    public static final String DISABLE_SUCCESS_MSG = "Daily Quotes have been disabled Successfully";
    public static final String RE_ENABLE_START = "To Re-Enable send " + QuoteCommand.START.getCommand();
    public static final String DISABLE_MSG = DISABLE_SUCCESS_MSG + NEXT_LINE + RE_ENABLE_START;
    private static final String ERROR_MESSAGE = "Oops!!! An Error at our End" + NEXT_LINE +
            "Please retry or Report to us by creating an issue";
    private static final String INVALID_MSG = "You have sent an Invalid Command " + NEXT_LINE + "Available Commands are : ";
    public static final String DEFAULT_INVALID_MSG = INVALID_MSG + NEXT_LINE + QuoteCommand.getAllAvailableCommands();


    private BotUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static SendMessage errorMessage(String chatId) {
        SendMessage sendingMessage = new SendMessage();
        sendingMessage.setChatId(chatId);

        sendingMessage.setText(ERROR_MESSAGE);

        return sendingMessage;
    }
}
