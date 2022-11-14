package com.example.monitoring.cancelOrder.dto;

import com.example.monitoring.common.dto.BaseRequest;
import javax.validation.constraints.AssertFalse;
import lombok.Getter;

@Getter

public class CancelOrderSearchRequest extends BaseRequest {
    private boolean success;
    private String account;

    public CancelOrderSearchRequest() {
    }

    @AssertFalse
    public boolean isNameNull() {
        return account == null || account.trim().isEmpty();
    }
}
