package com.dhivakar.quotegeneratorbot.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum UserCommand {
    START("/start", "Start Receiving Daily Random Quotes"),
    RANDOM_QUOTE("/randomQuote", "Get Random Quotes"),
    DISABLE_QUOTE_SCHEDULING("/disableQuote", "Stop Receiving Daily Random Quotes"),
    DEFAULT("DEFAULT", "Default Invalid Command");

    private static final Map<String, UserCommand> API_LOOKUP_MAP = initAPILookUpMap();
    private final String command;
    private final String description;

    UserCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    private static Map<String, UserCommand> initAPILookUpMap() {

        Map<String, UserCommand> apiLookUpMap = new HashMap<>();

        for (UserCommand userCommand : UserCommand.values()) {
            apiLookUpMap.put(userCommand.getCommand(), userCommand);
        }

        return apiLookUpMap;

    }

    public static UserCommand getAPIEnumFromValue(String value){

        String trimmedAPIvalue = value.trim().replace("@freshmotivationbot","");

        return API_LOOKUP_MAP.get(trimmedAPIvalue);

    }

    public static String getAllAvailableCommands() {

        StringBuilder builder = new StringBuilder();

        UserCommand[] commands = Arrays.copyOf(UserCommand.values(), UserCommand.values().length - 1);

        Arrays.stream(commands).forEach(i -> builder.append(i.getCommand()).append(" : ").append(i.getDescription()).append(BotUtil.NEXT_LINE));

        return builder.toString();
    }
}
