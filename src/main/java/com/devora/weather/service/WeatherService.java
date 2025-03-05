package com.devora.weather.service;

import com.devora.weather.dto.GeoLocation;
import com.devora.weather.dto.WeatherData;
import com.devora.weather.dto.WeatherResponse;
import com.devora.weather.dto.LocationRequest;
import com.devora.weather.exception.BadRequestException;
import com.devora.weather.model.Location;
import com.devora.weather.model.User;
import com.devora.weather.repository.LocationRepository;
import com.devora.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;


@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    private final RestTemplate restTemplate;
    @Value("${weather.api.url}")
    private String openWeatherUrl;
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url.city}")
    private String openWeatherUrlForCity;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    Mapper mapper;

    public WeatherData getWeatherForLocation(Location location) {
        String url = UriComponentsBuilder.fromUriString(openWeatherUrl)
                .queryParam("lat", location.getLatitude())
                .queryParam("lon", location.getLongitude())
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();
        try {
            log.info("Get weather from API for location {}", location.getName());
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
            if (response == null || response.getMain() == null || response.getWeather().isEmpty()) {
                throw new BadRequestException("Ошибка API: пустой ответ или некорректные данные.");
            }
            WeatherData weatherData = mapper.mapToWeatherData(response, location);
            log.info("Get weather from API for location {}", weatherData.getCity());
            return weatherData;

        } catch (HttpClientErrorException exception) {
            log.error("Ошибка запроса к API: {}", exception.getResponseBodyAsString());
            throw new BadRequestException("Неверный запрос к API: " + exception.getResponseBodyAsString());
        }

    }

    public List<GeoLocation> searchCity(String city) {
        String url = openWeatherUrlForCity + "?q=" + city + "&appid=" + apiKey + "&limit=5";

        try {
            log.info("Get weather for {}", url);
            log.info("Get weather from API for city {}", city);
            ResponseEntity<GeoLocation[]> geoResponse = restTemplate.getForEntity(url, GeoLocation[].class);
            if (geoResponse.getBody() == null || geoResponse.getBody().length == 0) {
                log.warn("City not found: {}", city);
            }
            List<GeoLocation> geoLocations = Arrays.asList(geoResponse.getBody());
            log.warn("geoLocations is empty? {}", geoLocations.isEmpty());
            return geoLocations;
        } catch (HttpClientErrorException exception) {
            log.error("Ошибка запроса к API: {}", exception.getResponseBodyAsString());
            throw new BadRequestException("Неверный запрос к API: " + exception.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Неизвестная ошибка: {}", e.getMessage());
            throw new RuntimeException("Ошибка при запросе к API", e);
        }

    }

    public List<WeatherData> getWeatherDataForUser(String username) {
        log.info("Get weather data for user {}", username);
        List<Location> locations = locationRepository.findAllByLogin(username);
        log.info("Get locations for user {}", username);
        return locations.stream()
                .map(this::getWeatherForLocation)
                .toList();
    }

    public void deleteWeatherForUser(String name, BigDecimal lat, BigDecimal lon) {
        log.info("Delete weather for city {}", name);
        locationRepository.deleteByName(name, lat, lon);
        log.info("Delete successful for city:  {}", name);

    }

    public void saveLocation(LocationRequest locationRequest, String username) {
        log.info("find User");
        User user = userRepository.findByLogin(username).orElseThrow(RuntimeException::new);
        log.warn("User {}", user);
        log.info("Save location: {}", locationRequest);
        Location location = new Location(null, locationRequest.getCity(), user,
                locationRequest.getLatitude(), locationRequest.getLongitude());
        locationRepository.save(location);
        log.info("Save successful:  {}", locationRequest);
    }
}
