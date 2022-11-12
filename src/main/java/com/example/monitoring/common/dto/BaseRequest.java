package com.example.monitoring.common.dto;

import com.example.monitoring.common.util.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.validation.constraints.AssertFalse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRequest {
    private static final String DAY = "1D";

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public BaseRequest() {
        LocalDateTime time = Date.toLocalDate(LocalDateTime.now(), DAY);
        this.startTime = time;
        this.endTime = LocalDateTime.now();
    }

    @AssertFalse
    private boolean isSameTime() {
        return Duration.between(startTime, endTime).getSeconds() < 60;
    }
}
