package com.example.monitoring.common.util.grade;

import org.springframework.stereotype.Component;

@Component
public class BronzeGrade implements Grade {

    @Override
    public String getGrade() {
        return "BRONZE";
    }
}
