package com.example.monitoring.common.exception;

public class NoMisMatchGradeException extends BaseException {
    private final static String MESSAGE = "grade 파라미터를 다시 확인해주세요.";

    public NoMisMatchGradeException() {
        super(MESSAGE);
    }
}
