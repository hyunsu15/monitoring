package com.example.monitoring.addCart.dto;

import com.example.monitoring.common.dto.BaseRequest;
import lombok.Getter;

@Getter
public class FailAddCartRequest extends BaseRequest {
    private double percent;
}
