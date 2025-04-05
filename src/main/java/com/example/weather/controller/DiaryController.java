package com.example.weather.controller;

import com.example.weather.domain.Diary;
import com.example.weather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/create/diary")
    public void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                            @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

}
