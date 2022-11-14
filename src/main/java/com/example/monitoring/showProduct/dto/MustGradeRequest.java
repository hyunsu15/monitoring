package com.example.monitoring.showProduct.dto;

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

    public MustGradeRequest() {
    }

    @AssertFalse
    private boolean isGradeNull() {
        return grade == null || grade.trim().isEmpty();
    }
}
