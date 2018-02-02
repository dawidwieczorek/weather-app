package com.example.weatherapp.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ForecastQuery {

    @NotNull
    private Long id;
}
