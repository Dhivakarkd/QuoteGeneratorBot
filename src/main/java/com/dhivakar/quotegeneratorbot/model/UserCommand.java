package com.dhivakar.quotegeneratorbot.model;

public enum UserCommand {
    START("/start"), RANDOM_QUOTE("/randomQuote"), DISABLE_QUOTE_SCHEDULING("/disableQuote");
    final String command;

    UserCommand(String command) {
        this.command = command;
    }
}
