package com.example.monitoring.signup.exception;

import com.example.monitoring.common.exception.BaseException;

public class NullNameException extends BaseException {
    private final static String MESSAGE = "해당 기능은 이름이 필요합니다.";

    public NullNameException() {
        super(MESSAGE);
    }
}
