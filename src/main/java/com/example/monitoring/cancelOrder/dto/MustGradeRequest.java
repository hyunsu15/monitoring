package com.example.monitoring.cancelOrder.dto;

import com.example.monitoring.common.dto.BaseRequest;
import javax.validation.constraints.AssertFalse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MustGradeRequest extends BaseRequest {
    String account;
    String productId;
    String grade;
    private boolean success;

    public MustGradeRequest() {
    }

    @AssertFalse
    private boolean isGradeNull() {
        return grade == null || grade.trim().isEmpty();
    }
}
