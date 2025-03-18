package com.devora.weather.service;

import com.devora.weather.dto.WeatherData;
import com.devora.weather.dto.response.WeatherResponse;
import com.devora.weather.dto.request.LocationRequest;
import com.devora.weather.exception.BadRequestException;
import com.devora.weather.mapper.Mapper;
import com.devora.weather.model.Location;
import com.devora.weather.model.User;
import com.devora.weather.repository.LocationRepository;
import com.devora.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final WeatherApiClient weatherApiClient;
    private final Mapper mapper;

    public WeatherData getWeatherForLocation(Location location) {
        WeatherResponse response = weatherApiClient.getWeatherForLocation(location.getLatitude(), location.getLongitude());
        if (response == null || response.getMain() == null || response.getWeather().isEmpty()) {
            throw new BadRequestException("Invalid API response.");
        }
        return mapper.mapToWeatherData(response, location);
    }

    public List<WeatherData> getWeatherDataForUser(String username) {
        log.info("Fetching weather for user: {}", username);
        List<Location> locations = locationRepository.findAllByLogin(username);
        return locations.stream()
                .map(this::getWeatherForLocation)
                .toList();
    }

    public void deleteWeatherForUser(String name, BigDecimal lat, BigDecimal lon, String login) {
        log.info("Deleting location: {}", name);
        locationRepository.deleteByNameAndCoordinate(name, lat, lon, login);
    }

    public void saveLocation(LocationRequest locationRequest, String username) {
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(RuntimeException::new);
        Location location = new Location(null, locationRequest.getCity(), user,
                locationRequest.getLatitude(), locationRequest.getLongitude());
        locationRepository.save(location);
    }
}
