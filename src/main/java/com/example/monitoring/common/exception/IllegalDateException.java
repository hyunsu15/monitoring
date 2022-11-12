package com.example.monitoring.common.exception;

public class IllegalDateException extends BaseException {
    private final static String MESSAGE = "적어도 1분 이상 차이 나게 해주세요.";

    public IllegalDateException() {
        super(MESSAGE);
    }
}