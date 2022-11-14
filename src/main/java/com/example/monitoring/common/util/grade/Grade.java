package com.example.monitoring.common.util.grade;

public interface Grade {
    int getGradeValue();

    String getGrade();

    default boolean isSame(String grade) {
        return grade != null
                && getGrade().toLowerCase().equals(grade.toLowerCase())
                && getGrade().toUpperCase().equals(grade.toUpperCase());
    }

}
