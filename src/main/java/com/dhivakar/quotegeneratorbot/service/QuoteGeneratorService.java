package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.model.BotUser;
import com.dhivakar.quotegeneratorbot.model.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class QuoteGeneratorService {
    private static final String QUOTES_ENDPOINT = "/quote/randomQuote";
    List<BotUser> userList = new ArrayList<>();
    // FIXME: 20/11/22 please default to env variable
    private String quotesApiDomain = "http://192.168.1.13:9093";

    public Quote generateQuote() {
        RestTemplate restTemplate = new RestTemplate();

        Quote q = restTemplate.getForObject(quotesApiDomain + QUOTES_ENDPOINT, Quote.class);
        if (q != null && q.getAuthor().isEmpty()) {
            q.setAuthor("Unknown");
        }

        return q;

    }

}
