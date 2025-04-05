package com.example.weather.service;

import com.example.weather.domain.Diary;
import com.example.weather.openApi.WeatherApiService;
import com.example.weather.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final WeatherApiService weatherApiService;

    public void createDiary(LocalDate date, String text) {
        Map<String, Object> weatherData = weatherApiService.getWeatherData();

        diaryRepository.save(Diary.builder()
            .weather(weatherData.get("main").toString())
            .icon(weatherData.get("icon").toString())
            .temperature((Double) weatherData.get("temp"))
            .date(date)
            .text(text)
            .build());
    }
}
