package com.example.monitoring.showProduct.service;

import com.example.monitoring.common.exception.NoSearchElementException;
import com.example.monitoring.showProduct.dto.ShowProductRequest;
import com.example.monitoring.showProduct.repository.ShowProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//ToDO : 로그삭제
@Service
@RequiredArgsConstructor
@Slf4j
public class ShowProductService {
    private final ShowProductRepository showProductRepository;

    public Long countShowProduct(ShowProductRequest request) {
        log.info("request = " + request.getProductId());
        long count = getCount(request);
        if (count == 0) {
            throw new NoSearchElementException();
        }
        return count;
    }

    private long getCount(ShowProductRequest request) {
        if (request.getProductId() == null) {
            return Long.valueOf(
                    showProductRepository
                            .findByShowTimeBetween(request.getStartTime(), request.getEndTime())
                            .size()
            );
        }
        return Long.valueOf(
                showProductRepository
                        .findByShowTimeBetweenAndProduct(request.getStartTime(), request.getEndTime(),
                                request.getProductId())
                        .size()
        );
    }
}
