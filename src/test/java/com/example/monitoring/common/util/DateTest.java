package com.example.monitoring.common.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.monitoring.common.exception.DateParseException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTest {
    @Test
    public void test() {
        LocalDateTime date = LocalDateTime.of(11, 11, 11, 0, 0);
        LocalDateTime expect = Date.toLocalDate(date, "1d");
        LocalDateTime result = LocalDateTime.of(11, 11, 10, 0, 0);
        assertThat(expect).isEqualTo(result);
    }

    @Test
    public void test2() {
        LocalDateTime date = LocalDateTime.of(2022, 11, 11, 0, 0);
        LocalDateTime expect = Date.toLocalDate(date, "11d");
        LocalDateTime result = LocalDateTime.of(2022, 10, 31, 0, 0);
        assertThat(expect).isEqualTo(result);
    }

    @Test
    public void test3() {
        LocalDateTime date = LocalDateTime.of(2022, 11, 11, 0, 0);
        LocalDateTime expect = Date.toLocalDate(date, "1h");
        LocalDateTime result = LocalDateTime.of(2022, 11, 10, 23, 0);
        assertThat(expect).isEqualTo(result);
    }

    @DisplayName("에러 테스트")
    @Test
    public void errorTest() {
        LocalDateTime date = LocalDateTime.of(2022, 11, 11, 0, 0);
        assertThatThrownBy(() -> Date.toLocalDate(date, "11d11d")).isInstanceOf(DateParseException.class);
    }

    @DisplayName("dhm 없음")
    @Test
    public void errorTest2() {
        LocalDateTime date = LocalDateTime.of(2022, 11, 11, 0, 0);
        assertThatThrownBy(() -> Date.toLocalDate(date, "11q11a")).isInstanceOf(DateParseException.class);
    }

    @DisplayName("dhm 없음")
    @Test
    public void errorTest3() {
        LocalDateTime date = LocalDateTime.of(2022, 11, 11, 0, 0);
        assertThatThrownBy(() -> Date.toLocalDate(date, null)).isInstanceOf(DateParseException.class);
    }
}