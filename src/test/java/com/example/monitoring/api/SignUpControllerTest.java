package com.example.monitoring.api;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.monitoring.signup.domain.SignUp;
import com.example.monitoring.signup.repository.SignUpRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SignUpRepository signUpRepository;


    @AfterEach
    void shutDown() {
        signUpRepository.deleteAll();
    }

    @Test
    void emptySignTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/signup/count")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString(StandardCharsets.UTF_8)).contains("검색 결과가 없습니다.");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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


    @Test
    void searchByNameTest() throws Exception {
        signUpRepository.save(SignUp.builder().name("qwe").signUpTime(LocalDateTime.now()).build());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/signup/search?name=qwe")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString()).contains("qwe");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void searchByNameErrorTest() throws Exception {
        signUpRepository.save(SignUp.builder().name("q").signUpTime(LocalDateTime.now()).build());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/signup/search?name=qwe")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString(StandardCharsets.UTF_8)).contains("검색 결과가 없습니다.");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addTest() throws Exception {
        SignUp sign = SignUp.builder().name("q").signUpTime(LocalDateTime.now()).build();
        MockHttpServletResponse response = mockMvc.perform(
                        post("/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sign))
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}