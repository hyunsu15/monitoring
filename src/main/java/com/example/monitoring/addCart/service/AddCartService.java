package com.example.monitoring.addCart.service;

import com.example.monitoring.addCart.domain.AddCart;
import com.example.monitoring.addCart.dto.AddCartRequest;
import com.example.monitoring.addCart.dto.FailAddCartRequest;
import com.example.monitoring.addCart.dto.FailAddCartResponse;
import com.example.monitoring.addCart.repository.AddCartRepository;
import com.example.monitoring.common.util.grade.Grade;
import com.example.monitoring.common.util.grade.GradeRouter;
import com.example.monitoring.showProduct.dto.MustGradeRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCartService {
    private final AddCartRepository addCartRepository;
    private final GradeRouter gradeRouter;

    public Long getCountWish(AddCartRequest request) {
        long count = getCount(
                request
                , () -> addCartRepository
                        .findByAddTimeBetween(request.getStartTime(), request.getEndTime())
                , () -> addCartRepository
                        .findByAddTimeBetweenAndProduct(request.getStartTime(), request.getEndTime(),
                                request.getProductId()));

        return count;
    }

    private long getCount(
            AddCartRequest request
            , Supplier<List<AddCart>> returnIfnull
            , Supplier<List<AddCart>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }

    private long getCount(
            MustGradeRequest request
            , Supplier<List<AddCart>> returnIfnull
            , Supplier<List<AddCart>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }


    public Long countGradeEquals(MustGradeRequest request) {
        Grade grade = gradeRouter.findByGradeElseThrow(request.getGrade());
        long count = getCount(
                request
                , () -> addCartRepository.findByAddTimeBetweenAndGrade(
                        request.getStartTime()
                        , request.getEndTime()
                        , grade.getGrade())
                , () -> addCartRepository.findByAddTimeBetweenAndProductIdAndGrade(
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
                    , () -> addCartRepository.findByAddTimeBetweenAndGrade(
                            request.getStartTime()
                            , request.getEndTime()
                            , grade.getGrade())
                    , () -> addCartRepository.findByAddTimeBetweenAndProductIdAndGrade(
                            request.getStartTime()
                            , request.getEndTime()
                            , request.getProductId()
                            , grade.getGrade()));
        }
        return count;
    }

    public List<FailAddCartResponse> getFailAddCarts(FailAddCartRequest request) {
        Map<String, Long> successMap = new HashMap<>();
        Map<String, Long> failMap = new HashMap<>();
        initMap(request, successMap, failMap);
        List<FailAddCartResponse> failList = new ArrayList<>();
        for (String productId : failMap.keySet()) {
            double percent =
                    (double) failMap.get(productId) / (failMap.get(productId) + successMap.getOrDefault(productId, 0L))
                            * 100;
            if (percent >= request.getPercent()) {
                failList.add(FailAddCartResponse.builder().productId(productId).percent(percent).build());
            }
        }
        return failList;
    }

    private void initMap(FailAddCartRequest request, Map<String, Long> successMap, Map<String, Long> failMap) {
        List<AddCart> addCarts = addCartRepository.findByAddTimeBetween(request.getStartTime(), request.getEndTime());
        for (AddCart addCart : addCarts) {
            if (addCart.isSuccess()) {
                successMap.merge(addCart.getProductId(), 1L, Long::sum);
            }
            if (!addCart.isSuccess()) {
                failMap.merge(addCart.getProductId(), 1L, Long::sum);
            }
        }
    }

    public void addAddCartRecord(AddCartRequest request) {
        addCartRepository.save(
                AddCart.builder()
                        .grade(request.getGrade())
                        .productId(request.getProductId())
                        .success(request.isSuccess())
                        .account(request.getAccount())
                        .build()
        );
    }
}
