package com.example.monitoring.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.monitoring.addCart.domain.AddCart;
import com.example.monitoring.cancelOrder.domain.CancelOrder;
import com.example.monitoring.cancelOrder.repository.CancelOrderRepository;
import com.example.monitoring.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CancelOrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    List<MongoRepository<? extends Object, ObjectId>> repositorys;
    @Autowired
    CancelOrderRepository repository;

    @Autowired
    OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        repositorys.stream()
                .forEach(CrudRepository::deleteAll);
    }

    @Test
    void addTest() throws Exception {
        AddCart addCart = AddCart.builder().account("q").grade("BRONZE").addTime(LocalDateTime.now()).build();
        MockHttpServletResponse response = mockMvc.perform(
                        post("/order/cancel")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(addCart))
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Nested
    class CountTest {
        private static final String URL = "/order/cancel";

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
            repository.save(CancelOrder.builder().cancelTime(LocalDateTime.now()).build());

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
            repository.save(CancelOrder.builder().productId("12").cancelTime(LocalDateTime.now()).build());

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
            repository.save(CancelOrder.builder().productId("12").cancelTime(LocalDateTime.now()).build());
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
            repository.save(CancelOrder.builder().productId("123").cancelTime(LocalDateTime.now()).build());

            MockHttpServletResponse response = mockMvc.perform(
                            get(URL)
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString()).isEqualTo("1");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }
    }

    @Nested
    class GradeTest {
        private static final String URL = "/order/cancel/count/grade";
        private static final String ERROR_MESSAGE = "grade 파라미터를 다시 확인해주세요.";

        @Test
        void emptyShowTest() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(
                            get(URL)
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString(StandardCharsets.UTF_8)).contains(ERROR_MESSAGE);
            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void zeroMismatchIdTest() throws Exception {
            repository.save(
                    CancelOrder.builder().grade("GOLD").productId("123").cancelTime(LocalDateTime.now()).build());
            MockHttpServletResponse response = mockMvc.perform(
                            get(URL + "?grade=bronze")
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo("0");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void oneMatchIdTest() throws Exception {
            repository.save(
                    CancelOrder.builder().grade("BRONZE").productId("123").cancelTime(LocalDateTime.now()).build());
            MockHttpServletResponse response = mockMvc.perform(
                            get(URL + "?grade=bronze")
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString()).isEqualTo("1");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

    }

    @Nested
    class GradeMoreThanTest {
        private static final String URL = "/order/cancel/count/grade/more";
        private static final String ERROR_MESSAGE = "grade 파라미터를 다시 확인해주세요.";

        @Test
        void emptyShowTest() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(
                            get(URL)
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString(StandardCharsets.UTF_8)).contains(ERROR_MESSAGE);
            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void zeroMismatchIdTest() throws Exception {
            repository.save(
                    CancelOrder.builder().grade("BRONZE").productId("123").cancelTime(LocalDateTime.now()).build());

            MockHttpServletResponse response = mockMvc.perform(
                            get(URL + "?grade=GOLD")
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString()).isEqualTo("0");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void oneMatchIdTest() throws Exception {
            String grade = "GOLD";
            String product = "123";
            repository.save(
                    CancelOrder.builder().grade("GOLD").productId("123").cancelTime(LocalDateTime.now()).build());

            MockHttpServletResponse response = mockMvc.perform(
                            get(URL + "?grade=bronze")
                    )
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo("1");
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

    }

    @Nested
    class CancelOrderTest {
        private static final String URL = "/order/cancel/percent";
        private static final String ERROR_MESSAGE = "퍼센트를 입력해주세요.";

        @Test
        void emptyShowTest() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(
                            get(URL)
                    )
                    .andReturn()
                    .getResponse();
            assertThat(objectMapper.readValue(
                    response.getContentAsString(StandardCharsets.UTF_8)
                    , List.class)).isEqualTo(new ArrayList());
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void zeroMismatchIdTest() throws Exception {
            repository.save(
                    CancelOrder.builder().grade("BRONZE").success(false).productId("123")
                            .cancelTime(LocalDateTime.now())
                            .build());

            MockHttpServletResponse response = mockMvc.perform(
                            get(URL + "?percent=100")
                    )
                    .andReturn()
                    .getResponse();
            assertThat(
                    objectMapper.readValue(
                            response.getContentAsString()
                            , List.class).size()).isEqualTo(0);
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void oneMatchIdTest() throws Exception {
            repository.save(
                    CancelOrder.builder().grade("GOLD").success(true).productId("123").cancelTime(LocalDateTime.now())
                            .build());

            MockHttpServletResponse response = mockMvc.perform(
                            get(URL + "?percent=100")
                    )
                    .andReturn()
                    .getResponse();
            assertThat(
                    objectMapper.readValue(
                            response.getContentAsString()
                            , List.class).size()).isEqualTo(1);
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

    }
}