package com.dhivakar.QuoteGeneratorBot.service;

import com.dhivakar.QuoteGeneratorBot.model.BotUser;
import com.dhivakar.QuoteGeneratorBot.model.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class QuoteGeneratorService {
    List<BotUser> userList = new ArrayList<>();
    private final String apiuser =System.getenv("API_REQUEST_USER");
    private final String apipassword=System.getenv("API_REQUEST_PASSWORD");
    String target = "https://quotegenerator123.herokuapp.com/randomQuote";

    public Quote generateQuote() {
        System.out.println("getting in Generate Quote");

        Response cb = ClientBuilder.newClient().target(target)
               // .register(new BasicAuthentication(apiuser,apipassword))
                .request(MediaType.APPLICATION_JSON_TYPE).get();
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



}
