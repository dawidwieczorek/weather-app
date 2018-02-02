package com.example.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastDetails {

    @JsonProperty("dt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;

    @JsonProperty("main")
    private Details details;


    public String getDateText() {
        return Optional.of(date).map(d -> DateTimeFormatter.ofPattern("YYYY-MM-dd").format(d)).orElse(null);
    }
}
