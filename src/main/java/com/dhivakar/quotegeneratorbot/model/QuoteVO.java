package com.dhivakar.quotegeneratorbot.model;

import com.dhivakar.quotegeneratorbot.event.utils.BotUtil;
import lombok.Data;

@Data
public class QuoteVO {
    private int id;
    private String author;
    private String quote;

    public String formattedQuote() {

        return quote + BotUtil.NEXT_LINE + BotUtil.TAB_SPACE + "- " + author;
    }
}
