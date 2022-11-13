package com.example.monitoring.signup.dto;


import com.example.monitoring.signup.domain.SignUp;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchResponse {
    private boolean success;
    private String name;
    private LocalDateTime signUpTime;

    @Builder
    private SearchResponse(boolean success, String name, LocalDateTime signUpTime) {
        this.success = success;
        this.name = name;
        this.signUpTime = signUpTime;
    }

    public static SearchResponse makeSearchResponse(SignUp sign) {
        return SearchResponse.builder()
                .name(sign.getName())
                .signUpTime(sign.getSignUpTime())
                .success(sign.isSuccess())
                .build();
    }

}
