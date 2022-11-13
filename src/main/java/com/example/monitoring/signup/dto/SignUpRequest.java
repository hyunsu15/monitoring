package com.example.monitoring.signup.dto;

import com.example.monitoring.common.dto.BaseRequest;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest extends BaseRequest {
    private boolean success;
    private String name;
    private LocalDateTime signUpTime;

    public SignUpRequest() {
        signUpTime = LocalDateTime.now();
    }

    public boolean isNameNull() {
        return name == null && name.trim().isEmpty();
    }
}
