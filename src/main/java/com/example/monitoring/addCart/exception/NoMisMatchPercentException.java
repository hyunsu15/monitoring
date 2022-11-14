package com.example.monitoring.addCart.exception;

import com.example.monitoring.common.exception.BaseException;

public class NoMisMatchPercentException extends BaseException {
    private final static String MESSAGE = "퍼센트를 입력해주세요.";

    public NoMisMatchPercentException() {
        super(MESSAGE);
    }
}

