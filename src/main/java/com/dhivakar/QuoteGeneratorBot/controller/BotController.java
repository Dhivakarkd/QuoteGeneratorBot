package com.dhivakar.QuoteGeneratorBot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    @RequestMapping("/")
    public String getStatus() {
        return "Bot is now Up and Running";
    }
}
