package com.dhivakar.quotegeneratorbot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum QuestionCommand {

    START("/start", null, true),
    QUESTION("/question", null, true),
    FUNNY("/funny", "FUNNY", true),
    WEIRD("/weird", "WEIRD", true),
    DEEP_QUESTION("/deepquestion", "DEEP_MEANING", true),
    DONT_ASK_IT("/dontaskit", "NON_SENSE", false),
    TRUTH_OR_DARE("/truthordare", null, true),
    TRUTH("/truth", "TRUTH", true),
    DARE("/dare", "DARE", true),
    NEVER_HAVE_I_EVER("/neverhaveiever", "NVR_HV_I_EVE", true),
    DEFAULT("DEFAULT", null, false);

    private static final Map<String, QuestionCommand> API_LOOKUP_MAP = initAPILookUpMap();
    private static final String COMMAND_SUFFIX = "@Helpmychatbot";
    private final String command;
    private final String databaseValue;
    private final boolean isAvailable;

    private static Map<String, QuestionCommand> initAPILookUpMap() {

        Map<String, QuestionCommand> apiLookUpMap = new HashMap<>();

        for (QuestionCommand questionCommand : QuestionCommand.values()) {

            apiLookUpMap.put(questionCommand.getCommand(), questionCommand);
        }

        return apiLookUpMap;

    }

    public static QuestionCommand getAPIEnumFromValue(String value) {

        String trimmedAPIValue = value.replace(COMMAND_SUFFIX, "");

        QuestionCommand quoteCommand = API_LOOKUP_MAP.get(trimmedAPIValue);

        return quoteCommand != null ? quoteCommand : QuestionCommand.DEFAULT;

    }
}
