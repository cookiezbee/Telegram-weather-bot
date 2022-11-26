package com.TelegramBot.WeatherBot.config;

public class Data {
    private String name;
    private int temp;
    private int feels_like;
    private int humidity;
    private String main;
    private Double wind;
    private String icon;
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public int getTemp() {
        return temp;
    }
    public void setTemp(int temp) {
        this.temp = temp;
    }
    public int getFeels_like() { return feels_like; }
    public void setFeels_like(int feels_like) { this.feels_like = feels_like; }
    public Double getWind() {
        return wind;
    }
    public void setWind(Double wind) {
        this.wind = wind;
    }
    public int getHumidity() {
        return humidity;
    }
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    public String getMain() {
        return main;
    }
    public void setMain(String main) {
        this.main = main.substring(0, 1).toUpperCase() + main.substring(1);
    }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
