package com.example.weather.scheduler;

import com.example.weather.openApi.OpenWeatherClient;
import com.example.weather.repository.DateWeatherRepository;
import com.example.weather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherScheduler {

    private final DiaryService diaryService;
    private final DateWeatherRepository dateWeatherRepository;
    private final OpenWeatherClient openWeatherClient;

    @Scheduled(cron = "0 0 1 * * *") // 매일 0시 실행
    public void saveTodayWeather() {
        dateWeatherRepository.save(openWeatherClient.getWeatherFromApi("seoul"));
    }
}
