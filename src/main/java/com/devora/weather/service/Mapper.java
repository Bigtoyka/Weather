package com.devora.weather.service;

import com.devora.weather.dto.WeatherData;
import com.devora.weather.dto.WeatherResponse;
import com.devora.weather.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Mapper {
    WeatherData mapToWeatherData(WeatherResponse weatherResponse, Location location) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(location.getName());
        weatherData.setCountry(weatherResponse.getSys().getCountry());
        weatherData.setTemperature(weatherResponse.getMain().getTemp());
        weatherData.setFeelsLike(weatherResponse.getMain().getFeels_like());
        weatherData.setHumidity(weatherResponse.getMain().getHumidity());
        weatherData.setDescription(weatherResponse.getWeather().getFirst().getDescription());
        weatherData.setLat(location.getLatitude());
        weatherData.setLon(location.getLongitude());
        weatherData.setIconUrl("https://openweathermap.org/img/wn/" + weatherResponse.getWeather().getFirst().getIcon() + "@4x.png");
        log.info("Получены данные: {}", weatherData);
        return weatherData;
    }
}
