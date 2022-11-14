package com.example.monitoring.addCart.dto;

import com.example.monitoring.common.dto.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartRequest extends BaseRequest {
    private String productId;
    private String account;
    private boolean success;
    private String grade;

    public AddCartRequest() {
    }
}
