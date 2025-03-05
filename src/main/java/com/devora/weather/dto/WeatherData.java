package com.devora.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {
    private String city;
    private String country;
    private double temperature;
    private double feelsLike;
    private String description;
    private int humidity;
    private String iconUrl;
    private BigDecimal lat;
    private BigDecimal lon;
}
