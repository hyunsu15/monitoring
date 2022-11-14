package com.example.monitoring.addCart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FailAddCartResponse {
    private String productId;
    private double percent;

    @Builder
    public FailAddCartResponse(String productId, double percent) {
        this.productId = productId;
        this.percent = percent;
    }
}
