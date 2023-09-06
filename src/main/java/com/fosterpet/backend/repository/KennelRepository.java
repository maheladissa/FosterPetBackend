package com.fosterpet.backend.repository;

import com.fosterpet.backend.collection.Kennel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KennelRepository extends MongoRepository <Kennel, String> {
    List<Kennel> findByKennelNameStartsWith(String name);
}
