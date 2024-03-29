package com.dhivakar.quotegeneratorbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfiguration {

    private final String url = System.getenv("DATASOURCE_URL");
    private final String username = System.getenv("DATASOURCE_USERNAME");
    private final String password = System.getenv("DATASOURCE_PASSWORD");

    @Bean
    public DataSource getDatasource() {

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        return dataSourceBuilder.build();
    }
}
