package com.example.monitoring.signup.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.monitoring.signup.domain.SignUp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class SignUpRepositoryTest {
    @Autowired
    SignUpRepository signUpRepository;

    @AfterEach
    public void shutdown() {
        signUpRepository.deleteAll();
    }

    @Test
    void emptyTest() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plus(1, ChronoUnit.DAYS);
        long except = signUpRepository.findBySignUpTimeBetween(startTime, endTime).size();
        int result = 0;
        assertThat(except).isEqualTo(result);
    }

    @Test
    void sizeOneTest() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plus(1, ChronoUnit.DAYS);
        LocalDateTime signTime = startTime.plus(1, ChronoUnit.MINUTES);

        signUpRepository.save(SignUp.builder().signUpTime(signTime).build());
        long except = signUpRepository.findBySignUpTimeBetween(startTime, endTime).size();
        int result = 1;
        assertThat(except).isEqualTo(result);
    }

    @DisplayName("끝시간 바운드 체크")
    @Test
    void checkMaxBoundTest() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plus(1, ChronoUnit.DAYS);
        LocalDateTime signTime = startTime.plus(1, ChronoUnit.DAYS);

        signUpRepository.save(SignUp.builder().signUpTime(signTime).build());
        long except = signUpRepository.findBySignUpTimeBetween(startTime, endTime).size();
        int result = 1;
        assertThat(except).isEqualTo(result);
    }

    @DisplayName("처음시간 바운드 체크")
    @Test
    void checkMinBoundTest() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plus(1, ChronoUnit.DAYS);
        LocalDateTime signTime = startTime;

        signUpRepository.save(SignUp.builder().signUpTime(signTime).build());
        long except = signUpRepository.findBySignUpTimeBetween(startTime, endTime).size();
        int result = 1;
        assertThat(except).isEqualTo(result);
    }

    @DisplayName("이름 찾기 체크")
    @Test
    void nameSearchTest() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plus(1, ChronoUnit.DAYS);
        LocalDateTime signTime = startTime;
        String name = "qwe";
        signUpRepository.save(SignUp.builder().name(name).signUpTime(signTime).build());
        long except = signUpRepository.findBySignUpTimeBetweenAndName(startTime, endTime, name).size();
        int result = 1;
        assertThat(except).isEqualTo(result);
    }

    @DisplayName("이름 찾기 없는 케이스 체크")
    @Test
    void nameSearchSizeZeroTest() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plus(1, ChronoUnit.DAYS);
        LocalDateTime signTime = startTime;
        String name = "qwe";
        signUpRepository.save(SignUp.builder().name(name).signUpTime(signTime).build());
        long except = signUpRepository.findBySignUpTimeBetweenAndName(startTime, endTime, "q").size();
        int result = 0;
        System.out.println("result = " + result);
        System.out.println("except = " + except);
        assertThat(except).isEqualTo(result);
    }
}