package com.example.weatherapp.controller;

import com.example.weatherapp.service.WeatherApiService;
import com.example.weatherapp.model.Forecast;
import com.example.weatherapp.model.ForecastQuery;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class WeatherController {

    private WeatherApiService weatherApiService;

    @PostMapping("/chart")
    public ResponseEntity<Forecast> getChartData(@Valid @RequestBody ForecastQuery query) {
        return ResponseEntity.ok(weatherApiService.getForecast(query.getId()));
    }
}
