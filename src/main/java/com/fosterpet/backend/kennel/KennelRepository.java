package com.fosterpet.backend.kennel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KennelRepository extends MongoRepository <Kennel, String> {
    List<Kennel> findByKennelNameStartsWith(String name);
    List<Kennel> findByOwnerUserId(String ownerId);
    List<Kennel> findAll();

}
