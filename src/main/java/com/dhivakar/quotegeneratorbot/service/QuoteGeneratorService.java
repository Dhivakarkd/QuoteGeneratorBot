package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.model.BotUser;
import com.dhivakar.quotegeneratorbot.model.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class QuoteGeneratorService {
    List<BotUser> userList = new ArrayList<>();

    @Value("${quotes.api.url}")
    private String quotesApiDomain;
    private static final String QUOTES_ENDPOINT="/quote/randomQuote";
    public Quote generateQuote() {
        RestTemplate restTemplate = new RestTemplate();

        Quote q = restTemplate.getForObject(quotesApiDomain+QUOTES_ENDPOINT, Quote.class);
        if (q != null && q.getAuthor().isEmpty()) {
            q.setAuthor("Unknown");
        }

        return q;

    }

}
