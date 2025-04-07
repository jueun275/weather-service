package com.example.weather.openApi;

import com.example.weather.domain.DateWeather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenWeatherClient {

    @Value("${openweatherapi.key}")
    private String API_KEY;

    public DateWeather getWeatherFromApi(String city) {
        Map<String, Object> parsedWeather = getWeatherData(city);

        return DateWeather.builder()
            .date(LocalDate.now())
            .weather(parsedWeather.get("main").toString())
            .icon(parsedWeather.get("icon").toString())
            .temperature((Double) parsedWeather.get("temp"))
            .build();
    }

    private  Map<String, Object> getWeatherData(String city) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;

        try {
            String response = fetchApiResponse(apiUrl);
            return parseWeather(response);
        } catch (Exception e) {
            log.error("문제가 발생했습니다", e);
            return new HashMap<>();
        }
    }

    private String fetchApiResponse(String apiUrl) throws IOException {
        BufferedReader br = getBufferedReader(apiUrl);
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        return response.toString();
    }

    private static BufferedReader getBufferedReader(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            return new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            return new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
    }

    public Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("main");

        if (mainData != null) {
            resultMap.put("temp", mainData.get("temp"));
        }

        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        if (weatherArray != null) {
            JSONObject weatherData = (JSONObject) weatherArray.get(0);
            resultMap.put("main", weatherData.get("main"));
            resultMap.put("icon", weatherData.get("icon"));
        }
        return resultMap;
    }
}
