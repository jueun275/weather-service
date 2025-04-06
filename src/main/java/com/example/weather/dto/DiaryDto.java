package com.example.weather.dto;

import com.example.weather.domain.Diary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class DiaryDto {

    private String weather;
    private String icon;
    private double temperature;
    private String text;
    private LocalDate date;

    @Builder
    public DiaryDto(String weather, String icon, double temperature, String text, LocalDate date) {
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
        this.text = text;
        this.date = date;
    }

    public static DiaryDto fromEntity(Diary diary) {
        return DiaryDto.builder()
            .weather(diary.getWeather())
            .icon(diary.getIcon())
            .temperature(diary.getTemperature())
            .text(diary.getText())
            .date(diary.getDate())
            .build();
    }
}
