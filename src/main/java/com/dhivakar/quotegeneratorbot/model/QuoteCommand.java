package com.dhivakar.quotegeneratorbot.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum QuoteCommand {
    START("/start", "Start Receiving Daily Random Quotes", false),
    RANDOM_QUOTE("/randomquote", "Get Random Quotes", false),

    RANDOM_IMAGE("/randomimage", "Get Random Images", false),
    DISABLE_QUOTE_SCHEDULING("/disablequote", "Stop Receiving Daily Random Quotes", false),
    DEFAULT("DEFAULT", "Default Invalid Command", true);

    private static final Map<String, QuoteCommand> API_LOOKUP_MAP = initAPILookUpMap();
    private final String command;
    private final String description;

    private final boolean isAdmin;


    QuoteCommand(String command, String description, boolean isAdmin) {
        this.command = command;
        this.description = description;
        this.isAdmin = isAdmin;
    }

    private static Map<String, QuoteCommand> initAPILookUpMap() {

        Map<String, QuoteCommand> apiLookUpMap = new HashMap<>();

        for (QuoteCommand quoteCommand : QuoteCommand.values()) {
            if (!quoteCommand.isAdmin()) {
                apiLookUpMap.put(quoteCommand.getCommand(), quoteCommand);
            }
        }

        return apiLookUpMap;

    }

    public static QuoteCommand getAPIEnumFromValue(String value) {

        String trimmedAPIValue = value.replace("@freshmotivationbot", "");

        QuoteCommand quoteCommand = API_LOOKUP_MAP.get(trimmedAPIValue);

        return quoteCommand != null ? quoteCommand : QuoteCommand.DEFAULT;

    }

    public static String getAllAvailableCommands() {

        StringBuilder builder = new StringBuilder();

        QuoteCommand[] commands = Arrays.copyOf(QuoteCommand.values(), QuoteCommand.values().length - 1);

        Arrays.stream(commands).forEach(i -> builder.append(i.getCommand()).append(" : ").append(i.getDescription()).append(BotUtil.NEXT_LINE));

        return builder.toString();
    }
}
