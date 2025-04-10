package com.example.weather.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String weather;

    @Column(nullable = false, length = 50)
    private String icon;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false, length = 500)
    private String text;

    @Column(nullable = false)
    private LocalDate date;

    public void updateText(String text) {
        this.text = text;
    }

    @Builder
    public Diary(String weather, String icon, double temperature, String text, LocalDate date) {
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
        this.text = text;
        this.date = date;
    }

    public static Diary formDateWeather(DateWeather dateWeather, String text) {
        return Diary.builder()
            .weather(dateWeather.getWeather())
            .icon(dateWeather.getIcon())
            .temperature(dateWeather.getTemperature())
            .date(dateWeather.getDate())
            .text(text)
            .build();
    }
}
