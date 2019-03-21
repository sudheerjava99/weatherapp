package com.weather.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.weather.exception.AppException;
import com.weather.model.Weather;
import com.weather.util.WeatherUtil;

@Service
public class WeatherServiceImpl implements WeatherService {
    private Logger LOG = Logger.getLogger(WeatherServiceImpl.class);
    @Autowired
    private WeatherUtil weatherUtil;

    @Override
    public List<Weather> getWeatherInfo(String[] cityList) {
        LOG.info("Entered getWeatherInfo for : " + Arrays.toString(cityList));
        List<Weather> weatherList = new ArrayList<>();
        try {
            Arrays.stream(cityList).forEach(city -> {
                try {
                    JSONObject json = weatherUtil.getWeatherData(city.trim());
                    JSONArray jsonArray = json.getJSONArray("weather");
                    Weather weather = new Weather(json.getString("name"));
                    if (jsonArray.length() > 0) {
                        weather.setDescription(((JSONObject) jsonArray.get(0)).getString("description"));
                    }
                    buildWeather(json, weather);
                    weatherList.add(weather);
                } catch (final HttpClientErrorException e) {
                    Weather weather = new Weather(city);
                    weather.setError("Unable to get weather details for " + city);
                    weatherList.add(weather);
                }
            });
        } catch (final Exception e) {
            throw new AppException(e.getMessage());
        }
        return weatherList;
    }

    private void buildWeather(JSONObject json, Weather weather) {
        JSONObject main = json.getJSONObject("main");
        weather.setTemperature(main.getInt("temp"));
        weather.setHumidity(main.getInt("humidity"));
        weather.setMinimumTemperature(main.getInt("temp_min"));
        weather.setMaximumTemperature(main.getInt("temp_max"));
    }
}