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

@Document(collection = "sign_up")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class SignUp {
    @Id
    private ObjectId id;
    @CreatedDate
    private LocalDateTime createdAt;
    @Indexed(expireAfterSeconds = 60 * 60 * 24 * 7)
    private LocalDateTime expiredAt;

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
