package com.example.model.weatherforecast;

import lombok.Data;

@Data
public class Forecast {
    private String date;
    private String dateLabel;
    private String telop;
    private Detail detail;
    private Temperature temperature;
    private ChanceOfRain chanceOfRain;
    private Image image;

}
