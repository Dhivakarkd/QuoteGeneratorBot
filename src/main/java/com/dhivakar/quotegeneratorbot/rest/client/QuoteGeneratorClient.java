package com.dhivakar.quotegeneratorbot.rest.client;

import com.dhivakar.quotegeneratorbot.model.QuoteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class QuoteGeneratorClient {
    private static final String QUOTES_ENDPOINT = "/quote/randomQuote";
    private final RestTemplate restTemplate;
    // FIXME: 20/11/22 please default to env variable
    private String quotesApiDomain = "http://192.168.1.13:9093";

    public QuoteGeneratorClient() {
        this.restTemplate = new RestTemplate();
    }

    public QuoteVO generateQuote() {


        QuoteVO q = restTemplate.getForObject(quotesApiDomain + QUOTES_ENDPOINT, QuoteVO.class);
        if (q != null && q.getAuthor().isEmpty()) {
            q.setAuthor("Unknown");
        }

        return q;

    }

}
