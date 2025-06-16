package com.apka.quickstart.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecastDto {
    private List<WeatherDayDto> forecast;
    private double averageTemperature;

    public List<WeatherDayDto> getForecast() {
        return forecast;
    }

    public void setForecast(List<WeatherDayDto> forecast) {
        this.forecast = forecast;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }
}
