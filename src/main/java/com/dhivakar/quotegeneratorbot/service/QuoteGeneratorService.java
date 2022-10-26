package com.dhivakar.quotegeneratorbot.service;

import com.dhivakar.quotegeneratorbot.model.BotUser;
import com.dhivakar.quotegeneratorbot.model.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class QuoteGeneratorService {
    List<BotUser> userList = new ArrayList<>();
    String target = "https://quotegenerator123.herokuapp.com/quote/randomQuote";

    public Quote generateQuote() {
        System.out.println("getting in Generate Quote");

        RestTemplate restTemplate = new RestTemplate();

        Quote q = restTemplate.getForObject(target, Quote.class);
        if (q != null && q.getAuthor().isEmpty()) {
            q.setAuthor("Unknown");
        }

        return q;

    }



}
