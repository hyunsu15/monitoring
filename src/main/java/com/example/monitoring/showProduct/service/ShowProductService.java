package com.example.monitoring.showProduct.service;

import com.example.monitoring.common.util.grade.Grade;
import com.example.monitoring.common.util.grade.GradeRouter;
import com.example.monitoring.showProduct.domain.ShowProduct;
import com.example.monitoring.showProduct.dto.MustGradeRequest;
import com.example.monitoring.showProduct.dto.ShowProductRequest;
import com.example.monitoring.showProduct.repository.ShowProductRepository;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//ToDO : 로그삭제
@Service
@RequiredArgsConstructor
@Slf4j
public class ShowProductService {
    private final ShowProductRepository showProductRepository;
    private final GradeRouter gradeRouter;

    public Long countShowProduct(ShowProductRequest request) {
        long count = getCount(
                request
                , () -> showProductRepository
                        .findByShowTimeBetween(request.getStartTime(), request.getEndTime())
                , () -> showProductRepository
                        .findByShowTimeBetweenAndProduct(request.getStartTime(), request.getEndTime(),
                                request.getProductId()));
        return count;
    }

    private long getCount(
            MustGradeRequest request
            , Supplier<List<ShowProduct>> returnIfnull
            , Supplier<List<ShowProduct>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }

    private long getCount(
            ShowProductRequest request
            , Supplier<List<ShowProduct>> returnIfnull
            , Supplier<List<ShowProduct>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }

    public Long countGradeEquals(MustGradeRequest request) {
        Grade grade = gradeRouter.findByGradeElseThrow(request.getGrade());
        long count = getCount(
                request
                , () -> showProductRepository.findByShowTimeBetweenAndGrade(
                        request.getStartTime()
                        , request.getEndTime()
                        , grade.getGrade())
                , () -> showProductRepository.findByShowTimeBetweenAndProductIdAndGrade(
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
                    , () -> showProductRepository.findByShowTimeBetweenAndGrade(
                            request.getStartTime()
                            , request.getEndTime()
                            , grade.getGrade())
                    , () -> showProductRepository.findByShowTimeBetweenAndProductIdAndGrade(
                            request.getStartTime()
                            , request.getEndTime()
                            , request.getProductId()
                            , grade.getGrade()));
        }
        return count;
    }

    public void addShowProductRecord(ShowProductRequest request) {
        showProductRepository.save(
                ShowProduct.builder()
                        .account(request.getAccount())
                        .productId(request.getProductId())
                        .grade(request.getGrade())
                        .showTime(request.getShowTime())
                        .build()
        );
    }
}
