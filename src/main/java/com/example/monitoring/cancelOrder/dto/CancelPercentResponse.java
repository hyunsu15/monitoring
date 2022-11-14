package com.example.monitoring.cancelOrder.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CancelPercentResponse {
    private String productId;
    private double percent;

    @Builder
    public CancelPercentResponse(String productId, double percent) {
        this.productId = productId;
        this.percent = percent;
    }
}
