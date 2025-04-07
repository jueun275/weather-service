package com.example.weather.scheduler;

import com.example.weather.domain.DateWeather;
import com.example.weather.domain.Diary;
import com.example.weather.openApi.OpenWeatherClient;
import com.example.weather.repository.DateWeatherRepository;
import com.example.weather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WeatherScheduler {

    private final DiaryService diaryService;
    private final DateWeatherRepository dateWeatherRepository;
    private final OpenWeatherClient openWeatherClient;

    @Scheduled(cron = "0 0 1 * * *") // 매일 0시 실행
    public void saveTodayWeather() {
        LocalDate today = LocalDate.now();

        Map<String, Object> weatherData = openWeatherClient.getWeatherData("seoul");

        dateWeatherRepository.save(DateWeather.builder()
            .weather(weatherData.get("main").toString())
            .icon(weatherData.get("icon").toString())
            .temperature((Double) weatherData.get("temp"))
            .date(LocalDate.now())
            .build());
    }
}
