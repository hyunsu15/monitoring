package com.example.monitoring.common.exception;

import com.example.monitoring.common.dto.ErrorResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public ErrorResponse toResponse() {
        return new ErrorResponse(getMessage());
    }
}