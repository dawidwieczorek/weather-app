package com.example.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Details {

    private double temp;

    @JsonProperty("temp_max")
    private double maxTemp;

    @JsonProperty("temp_min")
    private double minTemp;

    private double pressure;

    private double humidity;
}