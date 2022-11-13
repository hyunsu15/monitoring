package com.example.monitoring.showProduct.repository;


import com.example.monitoring.showProduct.domain.ShowProduct;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ShowProductRepository extends MongoRepository<ShowProduct, ObjectId> {
    @Query(value = "{ 'showTime' : {$gte : ?0, $lte: ?1 },'productId' : ?2 }")
    List<ShowProduct> findByShowTimeBetweenAndProduct(LocalDateTime startTime, LocalDateTime endTime, String productId);

    @Query(value = "{ 'showTime' : {$gte : ?0, $lte: ?1 } }")
    List<ShowProduct> findByShowTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
