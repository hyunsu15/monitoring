package com.example.monitoring.order.dto;

import com.example.monitoring.common.dto.BaseRequest;
import javax.validation.constraints.AssertFalse;
import lombok.Getter;

@Getter

public class OrderSearchRequest extends BaseRequest {
    private boolean success;
    private String account;

    public OrderSearchRequest() {
    }

    @AssertFalse
    public boolean isNameNull() {
        return account == null || account.trim().isEmpty();
    }
}
