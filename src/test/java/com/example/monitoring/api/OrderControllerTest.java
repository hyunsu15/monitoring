package com.example.monitoring.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.monitoring.order.domain.Order;
import com.example.monitoring.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
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
class OrderControllerTest {
    private static final String URL = "/order";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    List<MongoRepository<? extends Object, ObjectId>> repositorys;
    @Autowired
    OrderRepository repository;

    @AfterEach
    void tearDown() {
        repositorys.stream()
                .forEach(CrudRepository::deleteAll);
    }

    @Nested
    class CountTest {

        @Test
        void emptyShowTest() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(
                            get(URL)
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo("0");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        }

        @Test
        void oneShowTest() throws Exception {
            repository.save(Order.builder().orderTime(LocalDateTime.now()).build());

            MockHttpServletResponse response = mockMvc.perform(
                            get(URL)
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString()).isEqualTo("1");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void zeroMismatchIdTest() throws Exception {
            repository.save(Order.builder().productId("12").orderTime(LocalDateTime.now()).build());

            MockHttpServletResponse response = mockMvc.perform(
                            get(URL + "?productId=123")
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo("0");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void oneMatchIdTest() throws Exception {
            repository.save(Order.builder().productId("12").orderTime(LocalDateTime.now()).build());
            MockHttpServletResponse response = mockMvc.perform(
                            get(URL + "?productId=12")
                    )
                    .andReturn()
                    .getResponse();

            assertThat(response.getContentAsString()).isEqualTo("1");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void allSearchTest() throws Exception {
            repository.save(Order.builder().productId("123").orderTime(LocalDateTime.now()).build());

            MockHttpServletResponse response = mockMvc.perform(
                            get(URL)
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString()).isEqualTo("1");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }
    }

}