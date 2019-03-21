package com.weather.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WeatherWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        Class[] classes = new Class[] { WeatherConfig.class };
        return classes;
    }

    @Override
    protected String[] getServletMappings() {
        String[] mappings = new String[] { "/" };
        return mappings;
    }
}