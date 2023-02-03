package com.example.model.weatherforecast;

import java.util.List;

import lombok.Data;

@Data
public class Copyright {
    private String title;
    private String link;
    private Image image;
    private List<Provider> provider;

}
