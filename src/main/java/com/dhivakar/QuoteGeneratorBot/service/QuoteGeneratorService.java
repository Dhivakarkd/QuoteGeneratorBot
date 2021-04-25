package com.dhivakar.QuoteGeneratorBot.service;

import com.dhivakar.QuoteGeneratorBot.model.BotUser;
import com.dhivakar.QuoteGeneratorBot.model.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class QuoteGeneratorService {
    List<BotUser> userList = new ArrayList<>();
    String target = "https://quotegenerator123.herokuapp.com/randomQuote";

    public Quote generateQuote() {
        System.out.println("getting in Generate Quote");

        Response cb = ClientBuilder.newClient().target(target).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = cb.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        Quote q = null;
        try {
            q = mapper.readValue(s, Quote.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (q != null && q.getAuthor().isEmpty()) {
            q.setAuthor("Unknown");
        }

        return q;

    }

    public List<BotUser> getbotuser() {
        List<BotUser> list = new ArrayList<>();
        BotUser b = new BotUser();
        b.setChatID(1273845668);
        list.add(b);


        return list;
    }


}
