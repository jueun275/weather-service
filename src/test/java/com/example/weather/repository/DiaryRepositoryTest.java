package com.example.weather.repository;

import com.example.weather.domain.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DiaryRepositoryTest {

    @Autowired
    DiaryRepository diaryRepository;

    @Test
    void insertMemoTest() {
        //given
        Diary newDiary = Diary.builder()
            .icon("")
            .text("")
            .date(LocalDate.now())
            .weather("")
            .temperature(0.2)
            .build();

        //when
        diaryRepository.save(newDiary);
        //then
        List<Diary> diaryList = diaryRepository.findAll();
        assertTrue(diaryList.size() > 0);
    }

    @Test
    void findByIdTest() {
        //given
        Diary newDiary = Diary.builder()
            .icon("")
            .text("hello")
            .date(LocalDate.now())
            .weather("")
            .temperature(26.2)
            .build();

        //when
        Diary diary = diaryRepository.save(newDiary);
        System.out.println(diary.getId());
        //then
        Optional<Diary> result = diaryRepository.findById((long) diary.getId());
        assertEquals("hello", result.get().getText());
    }
}