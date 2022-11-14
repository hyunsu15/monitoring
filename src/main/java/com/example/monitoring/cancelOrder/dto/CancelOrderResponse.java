package com.example.monitoring.cancelOrder.dto;

import com.example.monitoring.cancelOrder.domain.CancelOrder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CancelOrderResponse {
    private boolean success;
    private String account;
    private LocalDateTime cancelTime;

    @Builder
    public CancelOrderResponse(boolean success, String account, LocalDateTime cancelTime) {
        this.success = success;
        this.account = account;
        this.cancelTime = cancelTime;
    }

    public static CancelOrderResponse makeOrderResponse(CancelOrder order) {
        return CancelOrderResponse.builder()
                .account(order.getAccount())
                .cancelTime(order.getCancelTime())
                .success(order.isSuccess())
                .build();
    }
}
