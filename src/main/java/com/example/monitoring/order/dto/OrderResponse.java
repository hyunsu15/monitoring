package com.example.monitoring.order.dto;

import com.example.monitoring.order.domain.Order;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {
    private boolean success;
    private String account;
    private LocalDateTime orderTime;

    @Builder
    public OrderResponse(boolean success, String account, LocalDateTime orderTime) {
        this.success = success;
        this.account = account;
        this.orderTime = orderTime;
    }

    public static OrderResponse makeOrderResponse(Order order) {
        return OrderResponse.builder()
                .account(order.getAccount())
                .orderTime(order.getOrderTime())
                .success(order.isSuccess())
                .build();
    }
}
