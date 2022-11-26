package com.TelegramBot.WeatherBot.config;

import com.vdurmont.emoji.EmojiParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class Weather {

    public static String emoji(Data weather) {
        switch (weather.getIcon()) {
            case "01d": return EmojiParser.parseToUnicode(":sunny:");
            case "01n": return EmojiParser.parseToUnicode(":full_moon:");
            case "02d":
            case "02n":
                return EmojiParser.parseToUnicode(":partly_sunny:");
            case "03d":
            case "03n":
                return EmojiParser.parseToUnicode(":white_sun_behind_cloud:");
            case "04d":
            case "04n":
                return EmojiParser.parseToUnicode(":cloud:");
            case "09d":
            case "09n":
                return EmojiParser.parseToUnicode(":white_sun_behind_cloud_rain:");
            case "10d":
            case "10n":
                return EmojiParser.parseToUnicode(":cloud_rain:");
            case "11d":
            case "11n":
                return EmojiParser.parseToUnicode(":thunder_cloud_rain:");
            case "13d":
            case "13n":
                return EmojiParser.parseToUnicode(":cloud_snow:");
            case "50d":
            case "50n":
                return EmojiParser.parseToUnicode(":fog:");
            default:
                return EmojiParser.parseToUnicode(":grey_question:");
        }
    }

    public static String getWeather(String city, Data weather) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=62056501369b77b3487fbef033af577c&units=metric&lang=ru");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        weather.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        weather.setTemp(main.getInt("temp"));
        weather.setFeels_like(main.getInt("feels_like"));
        weather.setHumidity(main.getInt("humidity"));
        JSONObject wind = object.getJSONObject("wind");
        weather.setWind(wind.getDouble("speed"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            weather.setMain((String) obj.get("description"));
            weather.setIcon((String) obj.get("icon"));
        }

        return "Погода в городе " + weather.getName() + "\n" +
                EmojiParser.parseToUnicode(":thermometer: Температура: ") + weather.getTemp() + "°C" + "\n" +
                EmojiParser.parseToUnicode(":thermometer: Ощущается " + weather.getFeels_like()) + "°C" + "\n" +
                EmojiParser.parseToUnicode(":droplet: Влажность: ") + weather.getHumidity() + "%" + "\n" +
                EmojiParser.parseToUnicode(":dash: Ветер: ") + weather.getWind() + " м/с" + "\n"
                + emoji(weather) + weather.getMain() + "\n";
    }
}