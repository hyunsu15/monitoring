package com.example.monitoring.common.config;

import com.example.monitoring.common.dto.ErrorResponse;
import com.example.monitoring.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //TODO : 나중에 로그 지우기
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> exceptedError(BaseException baseException) {
        log.info("잡힌 에러: {}", baseException.toResponse().getMessage());
        return ResponseEntity.badRequest().body(baseException.toResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> error(Exception exception) {
        log.info("error: {}", exception.getCause());
        log.info("error: {}", exception);

        return ResponseEntity.internalServerError().body(new ErrorResponse("예상치 못한 에러입니다."));
    }

}