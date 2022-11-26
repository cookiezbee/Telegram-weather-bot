package com.TelegramBot.WeatherBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Bean;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;

    static String myToken;
    static String myUsername;

    @Bean
    public void setBotToken() {
        myToken = token;
    }

    @Bean
    public void setUsername() {
        myUsername = botName;
    }
}
