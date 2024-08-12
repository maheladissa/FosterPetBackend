package com.fosterpet.backend.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByFirstNameStartsWith(String name);

    Optional<User> findByFirstName(String name);

    Optional<User> findByEmail(String email);

    User findByUserId(String id);

    Integer countUserByIsAccountActive(Boolean isAccountActive);

    @Query(value = "{ 'createdAt': { $gte: ?0, $lte: ?1 } }", count = true)
    Long countUsersByTimePeriod(Instant startDate, Instant endDate);

    @Query(value = "{ 'createdAt': { $gte: ?0, $lte: ?1 } }")
    List<User> findUsersByTimePeriod(Instant startDate, Instant endDate);
}
