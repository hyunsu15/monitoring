package com.example.monitoring.common.util.grade;

import org.springframework.stereotype.Component;

@Component
public class SliverGrade implements Grade {

    @Override
    public int getGradeValue() {
        return 1;
    }

    @Override
    public String getGrade() {
        return "SILVER";
    }
}
