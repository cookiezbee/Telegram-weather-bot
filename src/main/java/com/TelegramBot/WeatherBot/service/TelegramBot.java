package com.TelegramBot.WeatherBot.service;

import java.io.IOException;
import com.TelegramBot.WeatherBot.config.BotConfig;
import com.TelegramBot.WeatherBot.config.Data;
import com.TelegramBot.WeatherBot.config.Weather;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    public TelegramBot(BotConfig config) { this.config = config; }

    @Override
    public String getBotUsername() { return config.getBotName(); }

    @Override
    public String getBotToken() { return config.getToken(); }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Data weather = new Data();
            Message messageText = update.getMessage();
            long chatId = update.getMessage().getChatId();
            if (messageText != null && messageText.hasText()) {
                switch (messageText.getText()) {
                    case "/start" -> startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    case "/help" -> helpCommandReceived(chatId);
                    default -> {
                        try {
                            sendMessage(chatId, Weather.getWeather(messageText.getText(), weather));
                        } catch (IOException e) {
                            sendMessage(chatId, EmojiParser.parseToUnicode("Кажется, такого города не существует :woman_shrug: Введите корректное название города."));
                        }
                    }
                }
            }
        }
    }
    private void startCommandReceived(long chatId, String name){
        String answer = "Привет, " + name + EmojiParser.parseToUnicode(" :wave|type_1_2:") + "\n" +
                "Я — погодный бот. Напиши город, чтобы узнать погоду.";
        sendMessage(chatId,answer);
    }
    private void helpCommandReceived(long chatId){
        String answer = "Я — бот, который поможет узнать погоду в интересующем городе. Введите название города.";
        sendMessage(chatId,answer);
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try{
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
