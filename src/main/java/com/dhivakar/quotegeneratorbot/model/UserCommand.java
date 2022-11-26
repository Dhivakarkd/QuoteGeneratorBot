package com.dhivakar.quotegeneratorbot.model;

public enum UserCommand {
    START("/start"), RANDOM_QUOTE("/randomQuote"), DISABLE_QUOTE_SCHEDULING("/disableQuote"), LIST_CURRENT_USERS("/listActiveUsers");
    final String command;

    UserCommand(String command) {
        this.command = command;
    }
}
