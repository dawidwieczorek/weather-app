package com.example.weatherapp;

import com.example.weatherapp.exception.RateLimitExceededException;
import com.example.weatherapp.model.City;
import com.example.weatherapp.model.Coordinates;
import com.example.weatherapp.model.Forecast;
import com.example.weatherapp.service.WeatherApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WeatherApiServiceTest {

    @Autowired
    private WeatherApiService weatherApiService;

    @Test
    @DirtiesContext
    public void testGetForecast() {
        Long id = 6940463L;

        Forecast forecast = weatherApiService.getForecast(id);

        assertThat(forecast.getCity(), is(new City(id, "Altstadt", new Coordinates(11.5752D, 48.137D), "DE")));
        assertThat(forecast.getDetails(), not(empty()));
    }

    @Test(expected = RateLimitExceededException.class)
    @DirtiesContext
    public void testGetForecastLimiter() {
        for (long i = 0; i < 61; i++) {
            weatherApiService.getForecast(6940463L);
        }
    }

}