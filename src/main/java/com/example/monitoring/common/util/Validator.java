package com.example.monitoring.common.util;

import com.example.monitoring.common.exception.BaseException;
import java.util.function.Supplier;
import org.springframework.validation.BindingResult;

public class Validator extends BaseUtil {
    public static void checkError(BindingResult result, Supplier<? extends BaseException> supplier) {
        checkError(result.hasErrors(), supplier);
    }

    public static void checkError(boolean isError, Supplier<? extends BaseException> supplier) {
        if (isError) {
            throw supplier.get();
        }
    }
}
