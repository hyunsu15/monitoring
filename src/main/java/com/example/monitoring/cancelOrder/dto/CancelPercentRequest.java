package com.example.monitoring.cancelOrder.dto;

import com.example.monitoring.common.dto.BaseRequest;
import lombok.Getter;

@Getter
public class CancelPercentRequest extends BaseRequest {
    private double percent;
}
