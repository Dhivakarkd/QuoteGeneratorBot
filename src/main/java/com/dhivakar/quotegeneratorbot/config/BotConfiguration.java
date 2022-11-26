package com.dhivakar.quotegeneratorbot.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class BotConfiguration implements EnvironmentAware {


    private static Environment environment;


    public static String getBotName() {
        return environment.getProperty("BOT_NAME");
    }

    public static String getBottoken() {
        return environment.getProperty("BOT_TOKEN");
    }

    @Override
    public void setEnvironment(Environment environment) {
        BotConfiguration.environment = environment;
    }
}
