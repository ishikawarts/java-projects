package com.example.model.weatherforecast;

import java.util.List;

import lombok.Data;

@Data
public class WeatherForecast {
    private String publicTime;
    private String publicTimeFormatted;
    private String publishingOffice;
    private String title;
    private String link;
    private Description description;
    private List<Forecast> forecasts;
    private Location location;
    private Copyright copyright;

}
