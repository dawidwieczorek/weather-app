package com.example.weatherapp.service;

import com.example.weatherapp.aspect.RateLimit;
import com.example.weatherapp.model.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherApiService {

    @Value("${weather.api.forecastUri}")
    private String forecastUri;

    private final RestTemplate restTemplate;

    @RateLimit(value = 60)
    public Forecast getForecast(Long id) {
        return restTemplate.getForObject(forecastUri, Forecast.class, id);
    }

}
