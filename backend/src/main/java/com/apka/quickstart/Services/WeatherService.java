package com.apka.quickstart.Services;

import com.apka.quickstart.DTO.WeatherDayDto;
import com.apka.quickstart.DTO.WeatherForecastDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherForecastDto getForecast(String destination, LocalDate startDate, LocalDate endDate) {

        String geoUrl = String.format(
                "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s",
                destination, apiKey
        );

        ResponseEntity<JsonNode> geoResponse = restTemplate.getForEntity(geoUrl, JsonNode.class);
        JsonNode geoNode = geoResponse.getBody();
        if (geoNode == null || !geoNode.isArray() || geoNode.size() == 0) {
            throw new RuntimeException("Nie znaleziono lokalizacji: " + destination);
        }

        double lat = geoNode.get(0).get("lat").asDouble();
        double lon = geoNode.get(0).get("lon").asDouble();

        String weatherUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&units=metric&appid=%s",
                lat, lon, apiKey
        );

        ResponseEntity<JsonNode> weatherResponse = restTemplate.getForEntity(weatherUrl, JsonNode.class);
        JsonNode list = weatherResponse.getBody().get("list");

        if (list == null || !list.isArray()) {
            throw new RuntimeException("Brak danych pogodowych");
        }

        Map<LocalDate, List<Double>> temps = new HashMap<>();
        Map<LocalDate, List<String>> descriptions = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (JsonNode node : list) {
            String dtTxt = node.get("dt_txt").asText();
            LocalDate date = LocalDate.parse(dtTxt.substring(0, 10)); // YYYY-MM-DD

            if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
                double temp = node.get("main").get("temp").asDouble();
                String desc = node.get("weather").get(0).get("description").asText();

                temps.computeIfAbsent(date, k -> new ArrayList<>()).add(temp);
                descriptions.computeIfAbsent(date, k -> new ArrayList<>()).add(desc);
            }
        }

        List<WeatherDayDto> forecast = new ArrayList<>();
        double totalTemp = 0;
        int count = 0;

        for (LocalDate date : temps.keySet()) {
            List<Double> dayTemps = temps.get(date);
            double avgTemp = dayTemps.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            List<String> dayDescriptions = descriptions.get(date);
            String mostCommonDesc = dayDescriptions.stream()
                    .collect(Collectors.groupingBy(desc -> desc, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("brak opisu");

            forecast.add(new WeatherDayDto(date, avgTemp, mostCommonDesc));

            totalTemp += avgTemp;
            count++;
        }

        double averageTemperature = count > 0 ? totalTemp / count : 0.0;

        return new WeatherForecastDto(forecast, averageTemperature);
    }
}