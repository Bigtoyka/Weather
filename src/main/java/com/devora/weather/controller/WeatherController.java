package com.devora.weather.controller;

import com.devora.weather.dto.GeoLocation;
import com.devora.weather.dto.LocationRequest;
import com.devora.weather.dto.WeatherData;
import com.devora.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping()
@Slf4j
public class WeatherController {
    WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        String username = principal.getName();
        List<WeatherData> weatherList = weatherService.getWeatherDataForUser(username);
        model.addAttribute("username", username);
        model.addAttribute("weatherList", weatherList);
        return "index";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("city") String city,
                         @RequestParam("lon") BigDecimal lon,
                         @RequestParam("lat") BigDecimal lat) {
        weatherService.deleteWeatherForUser(city, lat, lon);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String add(@RequestParam("city") String city, Model model, Principal principal) {
        log.info("Получаем город: {}", city);
        List<GeoLocation> geoLocationList = weatherService.searchCity(city);
        model.addAttribute("username", principal.getName());
        model.addAttribute("city", city);
        model.addAttribute("geoLocationList", geoLocationList);
        return "search-results";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute LocationRequest locationRequest,
                       Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        weatherService.saveLocation(locationRequest, principal.getName());
        return "redirect:/";
    }
}
