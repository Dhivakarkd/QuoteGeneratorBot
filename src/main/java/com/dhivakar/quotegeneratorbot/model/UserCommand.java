package com.dhivakar.quotegeneratorbot.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.StringJoiner;

@Getter
public enum UserCommand {
    START("/start", "Start Receiving Daily Random Quotes"),
    RANDOM_QUOTE("/randomQuote", "Get Random Quotes"),
    DISABLE_QUOTE_SCHEDULING("/disableQuote", "Stop Receiving Daily Random Quotes"),
    DEFAULT("DEFAULT", "Default Invalid Command");
    private final String command;
    private final String description;

    UserCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static String getAllAvailableCommands(){

        StringBuilder builder= new StringBuilder();

        UserCommand[] commands = Arrays.copyOf(UserCommand.values(), UserCommand.values().length-1);

        Arrays.stream(commands).forEach(i-> builder.append(i.getCommand()).append(" : ").append(i.getDescription()).append(BotUtil.NEXT_LINE));

        return builder.toString();
    }
}
