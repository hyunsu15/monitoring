package com.example.monitoring.showProduct.domain;

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
public class ShowProduct {
    @Id
    private ObjectId id;
    @CreatedDate
    @Indexed(name = "deleteAt", expireAfter = "7d")
    private LocalDateTime createdAt;

    private String account;
    private String productId;
    private LocalDateTime showTime;

    private String grade;

    @Builder
    public ShowProduct(String account, String productId, LocalDateTime showTime, String grade) {
        this.account = account;
        this.productId = productId;
        this.showTime = showTime;
        this.grade = grade;
    }
}
