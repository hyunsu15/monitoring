package com.example.monitoring.order.service;

import com.example.monitoring.common.util.grade.Grade;
import com.example.monitoring.common.util.grade.GradeRouter;
import com.example.monitoring.order.domain.Order;
import com.example.monitoring.order.dto.FailOrderRequest;
import com.example.monitoring.order.dto.FailOrderResponse;
import com.example.monitoring.order.dto.MustGradeRequest;
import com.example.monitoring.order.dto.OrderRequest;
import com.example.monitoring.order.repository.OrderRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final GradeRouter gradeRouter;

    public Long getCountOrder(OrderRequest request) {
        long count = getCount(
                request
                , () -> orderRepository
                        .findByOrderTimeBetween(request.getStartTime(), request.getEndTime())
                , () -> orderRepository
                        .findByOrderTimeBetweenAndProduct(request.getStartTime(), request.getEndTime(),
                                request.getProductId()));

        return count;
    }

    private long getCount(
            OrderRequest request
            , Supplier<List<Order>> returnIfnull
            , Supplier<List<Order>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }

    private long getCount(
            MustGradeRequest request
            , Supplier<List<Order>> returnIfnull
            , Supplier<List<Order>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }

    public Long countGradeEquals(MustGradeRequest request) {
        Grade grade = gradeRouter.findByGradeElseThrow(request.getGrade());
        long count = getCount(
                request
                , () -> orderRepository.findByOrderTimeBetweenAndGrade(
                        request.getStartTime()
                        , request.getEndTime()
                        , grade.getGrade())
                , () -> orderRepository.findByOrderTimeBetweenAndProductIdAndGrade(
                        request.getStartTime()
                        , request.getEndTime()
                        , request.getProductId()
                        , grade.getGrade()));
        return count;
    }

    public Long countGradeMoreThan(MustGradeRequest request) {
        List<Grade> grades = gradeRouter.findByGradeListElseThrow(request.getGrade());
        long count = 0;
        for (Grade grade : grades) {
            count += getCount(
                    request
                    , () -> orderRepository.findByOrderTimeBetweenAndGrade(
                            request.getStartTime()
                            , request.getEndTime()
                            , grade.getGrade())
                    , () -> orderRepository.findByOrderTimeBetweenAndProductIdAndGrade(
                            request.getStartTime()
                            , request.getEndTime()
                            , request.getProductId()
                            , grade.getGrade()));
        }
        return count;
    }

    public List<FailOrderResponse> getFailOrders(FailOrderRequest request) {
        Map<String, Long> successMap = new HashMap<>();
        Map<String, Long> failMap = new HashMap<>();
        initMap(request, successMap, failMap);
        List<FailOrderResponse> failList = new ArrayList<>();
        for (String productId : failMap.keySet()) {
            double percent =
                    (double) failMap.get(productId) / (failMap.get(productId) + successMap.getOrDefault(productId, 0L))
                            * 100;
            if (percent >= request.getPercent()) {
                failList.add(FailOrderResponse.builder().productId(productId).percent(percent).build());
            }
        }
        return failList;
    }

    private void initMap(FailOrderRequest request, Map<String, Long> successMap, Map<String, Long> failMap) {
        List<Order> orders = orderRepository.findByOrderTimeBetween(request.getStartTime(), request.getEndTime());
        for (Order order : orders) {
            if (order.isSuccess()) {
                successMap.merge(order.getProductId(), 1L, Long::sum);
            }
            if (!order.isSuccess()) {
                failMap.merge(order.getProductId(), 1L, Long::sum);
            }
        }
    }
//
//    public void addAddCartRecord(AddCartRequest request) {
//        addCartRepository.save(
//                AddCart.builder()
//                        .grade(request.getGrade())
//                        .productId(request.getProductId())
//                        .success(request.isSuccess())
//                        .account(request.getAccount())
//                        .build()
//        );
//    }
}
