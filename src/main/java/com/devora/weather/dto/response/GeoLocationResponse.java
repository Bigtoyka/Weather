package com.devora.weather.dto.response;

import com.devora.weather.dto.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocationResponse {
    private List<GeoLocation> geoLocations;
}
