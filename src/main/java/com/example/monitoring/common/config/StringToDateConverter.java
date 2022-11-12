package com.example.monitoring.common.config;

import com.example.monitoring.common.util.Date;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringToDateConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        return Date.toLocalDate(LocalDateTime.now(), source);
    }
}
