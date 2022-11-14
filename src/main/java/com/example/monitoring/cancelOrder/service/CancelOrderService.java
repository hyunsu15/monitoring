package com.example.monitoring.cancelOrder.service;

import com.example.monitoring.cancelOrder.domain.CancelOrder;
import com.example.monitoring.cancelOrder.dto.CancelOrderRequest;
import com.example.monitoring.cancelOrder.dto.MustGradeRequest;
import com.example.monitoring.cancelOrder.repository.CancelOrderRepository;
import com.example.monitoring.common.util.grade.Grade;
import com.example.monitoring.common.util.grade.GradeRouter;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelOrderService {
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

//    public List<FailOrderResponse> getFailOrders(FailCancelOrderRequest request) {
//        Map<String, Long> successMap = new HashMap<>();
//        Map<String, Long> failMap = new HashMap<>();
//        initMap(request, successMap, failMap);
//        List<FailOrderResponse> failList = new ArrayList<>();
//        for (String productId : failMap.keySet()) {
//            double percent =
//                    (double) failMap.get(productId) / (failMap.get(productId) + successMap.getOrDefault(productId, 0L))
//                            * 100;
//            if (percent >= request.getPercent()) {
//                failList.add(FailOrderResponse.builder().productId(productId).percent(percent).build());
//            }
//        }
//        return failList;
//    }
//
//    private void initMap(FailCancelOrderRequest request, Map<String, Long> successMap, Map<String, Long> failMap) {
//        List<CancelOrder> orders = cancelOrderRepository.findByCancelOrderTimeBetween(request.getStartTime(),
//                request.getEndTime());
//        for (CancelOrder order : orders) {
//            if (order.isSuccess()) {
//                successMap.merge(order.getProductId(), 1L, Long::sum);
//            }
//            if (!order.isSuccess()) {
//                failMap.merge(order.getProductId(), 1L, Long::sum);
//            }
//        }
//    }

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

//    public List<OrderResponse> searchByName(OrderSearchRequest request) {
//        List<CancelOrder> orderList = cancelOrderRepository
//                .findBySignUpTimeBetweenAndAccount(request.getStartTime(), request.getEndTime(), request.getAccount());
//        if (orderList.isEmpty()) {
//            throw new NoSearchElementException();
//        }
//        return orderList.stream()
//                .map(x -> OrderResponse.makeOrderResponse(x))
//                .collect(Collectors.toList());
//    }
}
