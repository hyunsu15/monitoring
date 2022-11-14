package com.example.monitoring.addCart.service;

import com.example.monitoring.addCart.dto.AddCartRequest;
import com.example.monitoring.addCart.repository.AddCartRepository;
import com.example.monitoring.showProduct.domain.ShowProduct;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCartService {
    private final AddCartRepository addCartRepository;

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
            , Supplier<List<ShowProduct>> returnIfnull
            , Supplier<List<ShowProduct>> returnElse) {
        if (request.getProductId() == null) {
            return Long.valueOf(returnIfnull.get().size());
        }
        return Long.valueOf(returnElse.get().size());
    }

}
