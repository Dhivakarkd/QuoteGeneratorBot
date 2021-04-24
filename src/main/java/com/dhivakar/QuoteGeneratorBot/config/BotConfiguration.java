package com.dhivakar.QuoteGeneratorBot.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class BotConfiguration implements EnvironmentAware {


    private  static Environment environment;


    public static String getBotName() {
        String s=environment.getProperty("BOT_NAME");
        return s;
    }

    public static String getBottoken() {
        String s=environment.getProperty("BOT_TOKEN");
        return s;
    }

    @Override
    public void setEnvironment(Environment environment) {
        BotConfiguration.environment=environment;
    }
}
