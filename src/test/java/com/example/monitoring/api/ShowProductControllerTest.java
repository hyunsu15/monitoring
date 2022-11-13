package com.example.monitoring.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.monitoring.showProduct.domain.ShowProduct;
import com.example.monitoring.showProduct.repository.ShowProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ShowProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    List<MongoRepository<? extends Object, ObjectId>> repository;

    @Autowired
    ShowProductRepository showProductRepository;

    @AfterEach
    void tearDown() {
        repository.stream()
                .forEach(CrudRepository::deleteAll);
    }

    @Test
    void emptyShowTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/product/count")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString()).isEqualTo("0");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void oneShowTest() throws Exception {
        showProductRepository.save(ShowProduct.builder().showTime(LocalDateTime.now()).build());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/product/count")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString()).isEqualTo("1");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void zeroMismatchIdTest() throws Exception {
        showProductRepository.save(ShowProduct.builder().productId("123").showTime(LocalDateTime.now()).build());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/product/count?productId=1")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString()).isEqualTo("0");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void oneMatchIdTest() throws Exception {
        showProductRepository.save(ShowProduct.builder().productId("123").showTime(LocalDateTime.now()).build());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/product/count?productId=123")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString()).isEqualTo("1");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void allSearchTest() throws Exception {
        showProductRepository.save(ShowProduct.builder().productId("123").showTime(LocalDateTime.now()).build());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/product/count")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getContentAsString()).isEqualTo("1");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}