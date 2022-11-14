package com.example.monitoring.addCart.repository;

import com.example.monitoring.addCart.domain.AddCart;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AddCartRepository extends MongoRepository<AddCart, ObjectId> {
    @Query(value = "{ 'addTime' : {$gte : ?0, $lte: ?1 } }")
    List<AddCart> findByAddTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "{ 'addTime' : {$gte : ?0, $lte: ?1 },'productId' : ?2 }")
    List<AddCart> findByAddTimeBetweenAndProduct(LocalDateTime startTime, LocalDateTime endTime, String productId);

    @Query(value = "{ 'addTime' : {$gte : ?0, $lte: ?1 },'productId' : ?2,'grade': {$regex : ?3, $options: 'i' }}")
    List<AddCart> findByAddTimeBetweenAndProductIdAndGrade(LocalDateTime startTime, LocalDateTime endTime,
                                                           String productId, String grade);

    @Query(value = "{ 'addTime' : {$gte : ?0, $lte: ?1 },'grade': {$regex : ?2, $options: 'i'}}")
    List<AddCart> findByAddTimeBetweenAndGrade(LocalDateTime startTime, LocalDateTime endTime, String grade);
}
