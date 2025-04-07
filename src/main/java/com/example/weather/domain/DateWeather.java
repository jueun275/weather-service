package com.example.weather.domain;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class DateWeather {

    @Id
    private LocalDate date;

    @Column(nullable = false, length = 50)
    private String weather;

    @Column(nullable = false, length = 50)
    private String icon;

    @Nullable
    private double temperature;

    @Builder
    public DateWeather(LocalDate date, String weather, String icon, double temperature) {
        this.date = date;
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
    }
}
