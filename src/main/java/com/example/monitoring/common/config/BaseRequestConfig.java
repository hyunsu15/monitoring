package com.example.monitoring.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class BaseRequestConfig implements WebMvcConfigurer {
    private final StringToDateConverter stringToDateConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToDateConverter);
    }
}
