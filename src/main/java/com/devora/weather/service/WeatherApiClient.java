package com.devora.weather.service;

import com.devora.weather.dto.GeoLocation;
import com.devora.weather.dto.response.WeatherResponse;
import com.devora.weather.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherApiClient {
    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String openWeatherUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url.city}")
    private String openWeatherUrlForCity;

    public WeatherResponse getWeatherForLocation(BigDecimal latitude, BigDecimal longitude) {
        String url = UriComponentsBuilder.fromUriString(openWeatherUrl)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();
        try {
            log.info("Fetching weather for lat: {}, lon: {}", latitude, longitude);
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (HttpClientErrorException exception) {
            log.error("API request failed: {}", exception.getResponseBodyAsString());
            throw new BadRequestException("API error: " + exception.getResponseBodyAsString());
        }
    }

    public List<GeoLocation> searchCity(String city) {
        String url = openWeatherUrlForCity + "?q=" + city + "&appid=" + apiKey + "&limit=5";
        try {
            log.info("Searching city: {}", city);
            ResponseEntity<GeoLocation[]> response = restTemplate.getForEntity(url, GeoLocation[].class);
            return response.getBody() == null ? Collections.emptyList() : Arrays.asList(response.getBody());
        } catch (HttpClientErrorException exception) {
            log.error("API request failed: {}", exception.getResponseBodyAsString());
            throw new BadRequestException("API error: " + exception.getResponseBodyAsString());
        }
    }
}
