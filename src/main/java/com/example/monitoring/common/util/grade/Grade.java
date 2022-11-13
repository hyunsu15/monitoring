package com.example.monitoring.common.util.grade;

public interface Grade {

    String getGrade();

    default boolean isSame(String grade) {
        return grade != null
                && getGrade().equals(grade.toLowerCase())
                && getGrade().equals(grade.toUpperCase());
    }

}
