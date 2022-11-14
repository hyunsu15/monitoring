package com.example.monitoring.order.repository;

import com.example.monitoring.order.domain.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    @Query(value = "{ 'orderTime' : {$gte : ?0, $lte: ?1 } }")
    List<Order> findByOrderTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "{ 'orderTime' : {$gte : ?0, $lte: ?1 },'productId' : ?2 }")
    List<Order> findByOrderTimeBetweenAndProduct(LocalDateTime startTime, LocalDateTime endTime, String productId);

    @Query(value = "{ 'orderTime' : {$gte : ?0, $lte: ?1 },'productId' : ?2,'grade': {$regex : ?3, $options: 'i' }}")
    List<Order> findByOrderTimeBetweenAndProductIdAndGrade(LocalDateTime startTime, LocalDateTime endTime,
                                                           String productId, String grade);

    @Query(value = "{ 'orderTime' : {$gte : ?0, $lte: ?1 },'grade': {$regex : ?2, $options: 'i'}}")
    List<Order> findByOrderTimeBetweenAndGrade(LocalDateTime startTime, LocalDateTime endTime, String grade);

    @Query(value = "{ 'orderTime' : {$gte : ?0, $lte: ?1 }, 'account' : ?2 }")
    List<Order> findBySignUpTimeBetweenAndAccount(LocalDateTime startTime, LocalDateTime endTime, String account);
}
