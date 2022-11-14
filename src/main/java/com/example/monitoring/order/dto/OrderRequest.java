package com.example.monitoring.order.dto;

import com.example.monitoring.common.dto.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest extends BaseRequest {
    private String productId;
    private String account;
    private boolean success;
    private String grade;

    public OrderRequest() {
    }
}
