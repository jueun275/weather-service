package com.example.weather.service;

import com.example.weather.domain.DateWeather;
import com.example.weather.domain.Diary;
import com.example.weather.dto.DiaryDto;
import com.example.weather.openApi.OpenWeatherClient;
import com.example.weather.repository.DateWeatherRepository;
import com.example.weather.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;
    private final OpenWeatherClient openWeatherClient;

    private static final Logger logger = LoggerFactory.getLogger(DiaryService.class);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createDiary(LocalDate date, String text, String city) {
        logger.info("started to create diary");
        DateWeather dateWeather = getDateWeather(date,city);
        diaryRepository.save(Diary.formDateWeather(dateWeather, text));
        logger.info("end to create diary");
    }

    @Transactional(readOnly = true)
    public List<DiaryDto> readDiary(LocalDate date) {
        logger.debug("read diary");
        return diaryRepository.findAllByDate(date).stream()
            .map(DiaryDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DiaryDto> readDiaries(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findAllByDateBetween(startDate, endDate).stream()
            .map(DiaryDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateDiary(LocalDate date, String text) {
        Diary nowDiary = diaryRepository.getFirstByDate(date);
        nowDiary.updateText(text);
        diaryRepository.save(nowDiary);
    }

    @Transactional
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }

    private  DateWeather getDateWeather(LocalDate date, String city) {
        List<DateWeather> dateWeatherListFromDb = dateWeatherRepository.findAllByDate(date);
        if(dateWeatherListFromDb.size() == 0) {
            // DB에 없다면 과거의 날씨 데이터
            // 정책상,, 현대 날씨를 가져오도록하거나 날씨없이 일기를 쓰도록(과거의 날씨를 가져오는건 유로)
            return openWeatherClient.getWeatherFromApi(city);
        }else{
            return dateWeatherListFromDb.get(0);
        }
    }
}
