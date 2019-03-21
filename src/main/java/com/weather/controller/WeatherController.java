package com.weather.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.model.Weather;
import com.weather.service.WeatherService;

@RestController
public class WeatherController {
    private Logger LOG = Logger.getLogger(WeatherController.class);
    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/search")
    public List<Weather> getSearchResultViaAjax(@RequestParam String city) {
        LOG.info("Entered getSearchResultViaAjax for : " + city);

        String[] cities = Arrays.stream(city.split(",")).distinct().toArray(String[]::new);

        List<Weather> weatherResult = weatherService.getWeatherInfo(cities);

        LOG.info("Returning results : " + weatherResult.size());
        return weatherResult;
    }

}