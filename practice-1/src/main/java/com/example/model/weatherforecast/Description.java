package com.example.model.weatherforecast;

import lombok.Data;

@Data
public class Description {
    private String publicTime;
    private String publicTimeFormatted;
    private String headlineText;
    private String bodyText;
    private String text;

}
