package com.example.monitoring.signup.repository;

import com.example.monitoring.signup.domain.SignUp;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SignUpRepository extends MongoRepository<SignUp, ObjectId> {
    @Query(value = "{ 'signUpTime' : {$gte : ?0, $lte: ?1 }}")
    List<SignUp> countBySignUpTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
