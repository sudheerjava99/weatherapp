package com.weather.util;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherUtil {
    private RestTemplate template = new RestTemplate();
    private String url = "http://api.openweathermap.org/data/2.5/weather?q=",
            appId = "6f72d1b80810618e425fddd959fc64f4";

    public JSONObject getWeatherData(String query) {
        ResponseEntity<String> jsonResponse = template.getForEntity(url + query + "&appid=" + appId, String.class);
        JSONObject json = new JSONObject(jsonResponse.getBody());
        return json;
    }
}
