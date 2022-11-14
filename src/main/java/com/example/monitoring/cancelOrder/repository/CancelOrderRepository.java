package com.example.monitoring.cancelOrder.repository;

import com.example.monitoring.cancelOrder.domain.CancelOrder;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CancelOrderRepository extends MongoRepository<CancelOrder, ObjectId> {
    @Query(value = "{ 'cancelTime' : {$gte : ?0, $lte: ?1 } }")
    List<CancelOrder> findByCancelOrderTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "{ 'cancelTime' : {$gte : ?0, $lte: ?1 },'productId' : ?2 }")
    List<CancelOrder> findByCancelOrderTimeBetweenAndProduct(LocalDateTime startTime, LocalDateTime endTime,
                                                       String productId);

    @Query(value = "{ 'cancelTime' : {$gte : ?0, $lte: ?1 },'productId' : ?2,'grade': {$regex : ?3, $options: 'i' }}")
    List<CancelOrder> findByCancelOrderTimeBetweenAndProductIdAndGrade(LocalDateTime startTime, LocalDateTime endTime,
                                                                 String productId, String grade);

    @Query(value = "{ 'cancelTime' : {$gte : ?0, $lte: ?1 },'grade': {$regex : ?2, $options: 'i'}}")
    List<CancelOrder> findByCancelOrderTimeBetweenAndGrade(LocalDateTime startTime, LocalDateTime endTime, String grade);

    @Query(value = "{ 'cancelTime' : {$gte : ?0, $lte: ?1 }, 'account' : ?2 }")
    List<CancelOrder> findBySignUpTimeBetweenAndAccount(LocalDateTime startTime, LocalDateTime endTime, String account);
}
