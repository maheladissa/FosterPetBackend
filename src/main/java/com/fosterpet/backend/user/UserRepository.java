package com.fosterpet.backend.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByFirstNameStartsWith(String name);

    Optional<User> findByFirstName(String name);

    Optional<User> findByEmail(String email);

    User findByUserId(String id);
}
