package com.dhivakar.quotegeneratorbot.rest.client;

import com.dhivakar.quotegeneratorbot.model.ImageVO;
import com.dhivakar.quotegeneratorbot.model.QuoteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@Component
public class QuoteGeneratorClient {
    private static final String QUOTES_ENDPOINT = "/quote/randomQuote";
    private static final String IMAGE_ENDPOINT = "/image/file";
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

    public InputStream getFile() {

        ImageVO q = restTemplate.getForObject(quotesApiDomain + IMAGE_ENDPOINT, ImageVO.class);

        return new ByteArrayInputStream(q.getFileContent());
    }

}
