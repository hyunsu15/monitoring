package com.example.monitoring.cancelOrder.service;

import com.example.monitoring.cancelOrder.domain.CancelOrder;
import com.example.monitoring.cancelOrder.dto.CancelOrderRequest;
import com.example.monitoring.cancelOrder.dto.CancelOrderResponse;
import com.example.monitoring.cancelOrder.dto.CancelOrderSearchRequest;
import com.example.monitoring.cancelOrder.dto.CancelPercentRequest;
import com.example.monitoring.cancelOrder.dto.CancelPercentResponse;
import com.example.monitoring.cancelOrder.dto.MustGradeRequest;
import com.example.monitoring.cancelOrder.repository.CancelOrderRepository;
import com.example.monitoring.common.exception.NoSearchElementException;
import com.example.monitoring.common.util.grade.Grade;
import com.example.monitoring.common.util.grade.GradeRouter;
import com.example.monitoring.order.domain.Order;
import com.example.monitoring.order.repository.OrderRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelOrderService {
    private final OrderRepository orderRepository;
    private final CancelOrderRepository cancelOrderRepository;
    private final GradeRouter gradeRouter;

    public Long getCountOrder(CancelOrderRequest request) {
        long count = getCount(
                request
                , () -> cancelOrderRepository
                        .findByCancelOrderTimeBetween(request.getStartTime(), request.getEndTime())
                , () -> cancelOrderRepository
                        .findByCancelOrderTimeBetweenAndProduct(request.getStartTime(), request.getEndTime(),
                                request.getProductId()));

        return count;
    }

    private long getCount(
            CancelOrderRequest request
            , Supplier<List<CancelOrder>> returnIfnull
            , Supplier<List<CancelOrder>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }

    private long getCount(
            MustGradeRequest request
            , Supplier<List<CancelOrder>> returnIfnull
            , Supplier<List<CancelOrder>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }

    public Long countGradeEquals(MustGradeRequest request) {
        Grade grade = gradeRouter.findByGradeElseThrow(request.getGrade());
        long count = getCount(
                request
                , () -> cancelOrderRepository.findByCancelOrderTimeBetweenAndGrade(
                        request.getStartTime()
                        , request.getEndTime()
                        , grade.getGrade())
                , () -> cancelOrderRepository.findByCancelOrderTimeBetweenAndProductIdAndGrade(
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
                    , () -> cancelOrderRepository.findByCancelOrderTimeBetweenAndGrade(
                            request.getStartTime()
                            , request.getEndTime()
                            , grade.getGrade())
                    , () -> cancelOrderRepository.findByCancelOrderTimeBetweenAndProductIdAndGrade(
                            request.getStartTime()
                            , request.getEndTime()
                            , request.getProductId()
                            , grade.getGrade()));
        }
        return count;
    }


    public void addOrderRecord(CancelOrderRequest request) {
        cancelOrderRepository.save(
                CancelOrder.builder()
                        .grade(request.getGrade())
                        .productId(request.getProductId())
                        .success(request.isSuccess())
                        .account(request.getAccount())
                        .build()
        );
    }

    public List<CancelPercentResponse> getCancelPercent(CancelPercentRequest request) {
        Map<String, Long> orderMap = initOrderMap(request);
        Map<String, Long> cancelMap = initCancelOrderMap(request);
        List<CancelPercentResponse> cancelOrderList = new ArrayList<>();
        for (String productId : cancelMap.keySet()) {
            double percent =
                    (double) cancelMap.get(productId) / orderMap.getOrDefault(productId, cancelMap.get(productId))
                            * 100;
            if (percent >= request.getPercent()) {
                cancelOrderList.add(CancelPercentResponse.builder().productId(productId).percent(percent).build());
            }
        }
        return cancelOrderList;

    }

    private Map<String, Long> initCancelOrderMap(CancelPercentRequest request) {
        Map<String, Long> cancelMap = new HashMap<>();
        List<CancelOrder> cancelOrderList = cancelOrderRepository.findByCancelOrderTimeBetween(
                request.getStartTime(), request.getEndTime());
        for (CancelOrder cancelOrder : cancelOrderList) {
            if (cancelOrder.isSuccess()) {
                cancelMap.merge(cancelOrder.getProductId(), 1L, Long::sum);
            }
        }
        return cancelMap;
    }

    private Map<String, Long> initOrderMap(CancelPercentRequest request) {
        Map<String, Long> orderMap = new HashMap<>();
        List<Order> OrderList = orderRepository
                .findByOrderTimeBetween(request.getStartTime(), request.getEndTime());
        for (Order order : OrderList) {
            if (order.isSuccess()) {
                orderMap.merge(order.getProductId(), 1L, Long::sum);
            }
        }
        return orderMap;
    }

    public List<CancelOrderResponse> searchByName(CancelOrderSearchRequest request) {
        List<CancelOrder> orderList = cancelOrderRepository
                .findBySignUpTimeBetweenAndAccount(request.getStartTime(), request.getEndTime(), request.getAccount());
        if (orderList.isEmpty()) {
            throw new NoSearchElementException();
        }
        return orderList.stream()
                .map(x -> CancelOrderResponse.makeOrderResponse(x))
                .collect(Collectors.toList());
    }
}
