package com.example.weather.service;

import com.example.weather.domain.Diary;
import com.example.weather.dto.DiaryDto;
import com.example.weather.openApi.WeatherApiService;
import com.example.weather.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<DiaryDto> readDiary(LocalDate date) {
        return diaryRepository.findAllByDate(date).stream()
            .map(DiaryDto::fromEntity)
            .collect(Collectors.toList());
    }

    public List<DiaryDto> readDiaries(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findAllByDateBetween(startDate, endDate).stream()
            .map(DiaryDto::fromEntity)
            .collect(Collectors.toList());
    }

    public void updateDiary(LocalDate date, String text) {
        Diary nowDiary = diaryRepository.getFirstByDate(date);
        nowDiary.updateText(text);
        diaryRepository.save(nowDiary);
    }
    
    @Transactional
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }
}
