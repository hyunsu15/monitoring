package com.example.monitoring.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FailOrderResponse {
    private String productId;
    private double percent;

    @Builder
    public FailOrderResponse(String productId, double percent) {
        this.productId = productId;
        this.percent = percent;
    }
}
