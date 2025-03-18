package com.devora.weather.dto.response;

import com.devora.weather.dto.Coordinate;
import com.devora.weather.dto.MainData;
import com.devora.weather.dto.SysData;
import com.devora.weather.dto.WeatherDescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private Coordinate coord;
    private String name;
    private MainData main;
    private List<WeatherDescription> weather;
    private SysData sys;
}
