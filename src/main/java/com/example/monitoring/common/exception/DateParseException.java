package com.example.monitoring.common.exception;

public class DateParseException extends BaseException {
    private final static String MESSAGE = "d h m 순으로 d,h,m은 하나씩만 넣어주세요.";

    public DateParseException() {
        super(MESSAGE);
    }
}
