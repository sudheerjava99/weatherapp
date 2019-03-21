package com.weather.service;

import java.util.List;

import com.weather.model.Weather;

public interface WeatherService {

    List<Weather> getWeatherInfo(String[] cities);

}
