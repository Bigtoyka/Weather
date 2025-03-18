package com.devora.weather.controller;

import com.devora.weather.dto.GeoLocation;
import com.devora.weather.dto.request.LocationRequest;
import com.devora.weather.dto.WeatherData;
import com.devora.weather.security.CustomUserDetails;
import com.devora.weather.service.WeatherApiClient;
import com.devora.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.math.BigDecimal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final WeatherApiClient weatherApiClient;

    @GetMapping()
    public String index(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        List<WeatherData> weatherList = weatherService.getWeatherDataForUser(username);
        model.addAttribute("username", username);
        model.addAttribute("weatherList", weatherList);
        return "index";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("city") String city,
                         @RequestParam("lon") BigDecimal lon,
                         @RequestParam("lat") BigDecimal lat,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        weatherService.deleteWeatherForUser(city, lat, lon, customUserDetails.getUsername());
        return "redirect:/";
    }

    @PostMapping("/add")
    public String add(@RequestParam("city") String city, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("Get city: {}", city);
        List<GeoLocation> geoLocationList = weatherApiClient.searchCity(city);
        model.addAttribute("username", customUserDetails.getUsername());
        model.addAttribute("city", city);
        model.addAttribute("geoLocationList", geoLocationList);
        return "search-results";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute LocationRequest locationRequest,
                       Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("username", customUserDetails.getUsername());
        weatherService.saveLocation(locationRequest, customUserDetails.getUsername());
        return "redirect:/";
    }
}
