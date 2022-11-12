package com.example.monitoring.api;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.monitoring.signup.domain.SignUp;
import com.example.monitoring.signup.repository.SignUpRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    SignUpRepository signUpRepository;

    @Test
    void emptySignTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/signup/count")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString()).isEqualTo("0");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void oneSignTest() throws Exception {
        signUpRepository.save(SignUp.builder().signUpTime(LocalDateTime.now()).build());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/signup/count")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString()).isEqualTo("1");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @AfterEach
    void shutDown() {
        signUpRepository.deleteAll();
    }
}