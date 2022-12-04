package com.dhivakar.quotegeneratorbot.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum UserCommand {
    START("/start", "Start Receiving Daily Random Quotes", false),
    RANDOM_QUOTE("/randomquote", "Get Random Quotes", false),

    RANDOM_IMAGE("/randomimage", "Get Random Images", false),
    DISABLE_QUOTE_SCHEDULING("/disablequote", "Stop Receiving Daily Random Quotes", false),
    DEFAULT("DEFAULT", "Default Invalid Command", true);

    private static final Map<String, UserCommand> API_LOOKUP_MAP = initAPILookUpMap();
    private final String command;
    private final String description;

    private final boolean isAdmin;


    UserCommand(String command, String description, boolean isAdmin) {
        this.command = command;
        this.description = description;
        this.isAdmin = isAdmin;
    }

    private static Map<String, UserCommand> initAPILookUpMap() {

        Map<String, UserCommand> apiLookUpMap = new HashMap<>();

        for (UserCommand userCommand : UserCommand.values()) {
            if (!userCommand.isAdmin()) {
                apiLookUpMap.put(userCommand.getCommand(), userCommand);
            }
        }

        return apiLookUpMap;

    }

    public static UserCommand getAPIEnumFromValue(String value) {

        String trimmedAPIValue = value.replace("@freshmotivationbot", "");

        UserCommand userCommand = API_LOOKUP_MAP.get(trimmedAPIValue);

        return userCommand != null ? userCommand : UserCommand.DEFAULT;

    }

    public static String getAllAvailableCommands() {

        StringBuilder builder = new StringBuilder();

        UserCommand[] commands = Arrays.copyOf(UserCommand.values(), UserCommand.values().length - 1);

        Arrays.stream(commands).forEach(i -> builder.append(i.getCommand()).append(" : ").append(i.getDescription()).append(BotUtil.NEXT_LINE));

        return builder.toString();
    }
}
