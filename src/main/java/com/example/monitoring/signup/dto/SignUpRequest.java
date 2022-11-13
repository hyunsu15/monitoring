package com.example.monitoring.signup.dto;

import com.example.monitoring.common.dto.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest extends BaseRequest {
    private boolean success;
    private String name;

    public boolean isNameNull() {
        return name == null && name.trim().isEmpty();
    }
}
