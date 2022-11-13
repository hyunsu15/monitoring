package com.example.monitoring.common.exception;

public class NoSearchElementException extends BaseException {
    private final static String MESSAGE = "검색 결과가 없습니다.";

    public NoSearchElementException() {
        super(MESSAGE);
    }
}
