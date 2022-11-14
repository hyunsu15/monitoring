package com.example.monitoring.common.util.grade;

import org.springframework.stereotype.Component;

@Component
public class GoldGrade implements Grade {
    @Override
    public int getGradeValue() {
        return 2;
    }

    @Override
    public String getGrade() {
        return "GOLD";
    }
}
