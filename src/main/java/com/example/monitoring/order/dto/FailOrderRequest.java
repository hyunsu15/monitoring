package com.example.monitoring.order.dto;

import com.example.monitoring.common.dto.BaseRequest;
import lombok.Getter;

@Getter
public class FailOrderRequest extends BaseRequest {
    private double percent;
}
