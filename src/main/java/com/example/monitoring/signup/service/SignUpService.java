package com.example.monitoring.signup.service;

import com.example.monitoring.signup.dto.SignUpRequest;
import com.example.monitoring.signup.repository.SignUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final SignUpRepository signUpRepository;

    public Long getCount(SignUpRequest request) {
        return Long.valueOf(
                signUpRepository
                        .countBySignUpTimeBetween(request.getStartTime(), request.getEndTime())
                        .size()
        );
    }
}
