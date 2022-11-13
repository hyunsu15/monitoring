package com.example.monitoring.signup.service;

import com.example.monitoring.common.exception.NoSearchElementException;
import com.example.monitoring.signup.domain.SignUp;
import com.example.monitoring.signup.dto.SearchResponse;
import com.example.monitoring.signup.dto.SignUpRequest;
import com.example.monitoring.signup.repository.SignUpRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final SignUpRepository signUpRepository;

    public Long getCount(@NotNull SignUpRequest request) {
        long count = signUpRepository
                .findBySignUpTimeBetween(request.getStartTime(), request.getEndTime())
                .size();
        if (count == 0) {
            throw new NoSearchElementException();
        }
        return count;
    }

    public List<SearchResponse> searchByName(SignUpRequest request) {
        List<SignUp> signList = signUpRepository
                .findBySignUpTimeBetweenAndName(request.getStartTime(), request.getEndTime(), request.getName());
        if (signList.isEmpty()) {
            throw new NoSearchElementException();
        }
        return signList.stream()
                .map(x -> SearchResponse.makeSearchResponse(x))
                .collect(Collectors.toList());
    }

    public void addSignUpRecord(SignUpRequest request) {
        signUpRepository.save(
                SignUp.builder()
                        .name(request.getName())
                        .success(request.isSuccess())
                        .signUpTime(request.getSignUpTime())
                        .build()
        );
    }
}
