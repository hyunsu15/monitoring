package com.example.monitoring.common.util;

import com.example.monitoring.common.exception.BaseException;

public class BaseUtil {
    private final static String MESSAGE = "it is Utility class";

    protected BaseUtil() {
        throw new BaseException(MESSAGE);
    }
}