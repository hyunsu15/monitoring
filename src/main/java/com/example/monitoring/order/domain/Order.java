package com.example.monitoring.order.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    private ObjectId id;
    @CreatedDate
    @Indexed(name = "deleteAt", expireAfter = "7d")
    private LocalDateTime createdAt;

    private String productId;
    private String account;
    private boolean success;
    private String grade;

    private LocalDateTime orderTime;

    @Builder
    public Order(String productId, String account, boolean success, String grade, LocalDateTime orderTime) {
        this.productId = productId;
        this.account = account;
        this.success = success;
        this.grade = grade;
        this.orderTime = orderTime;
    }
}
