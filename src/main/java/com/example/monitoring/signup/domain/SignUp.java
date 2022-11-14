package com.example.monitoring.signup.domain;

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
public class SignUp {
    @Id
    private ObjectId id;
    @CreatedDate
    @Indexed(name = "deleteAt", expireAfter = "7d")
    private LocalDateTime createdAt;
    private boolean success;
    private String name;
    private LocalDateTime signUpTime;

    @Builder
    public SignUp(boolean success, String name, LocalDateTime signUpTime) {
        this.success = success;
        this.name = name;
        this.signUpTime = signUpTime;
    }
}
