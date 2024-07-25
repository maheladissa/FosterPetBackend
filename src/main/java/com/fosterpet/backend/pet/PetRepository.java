package com.fosterpet.backend.pet;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PetRepository extends MongoRepository <Pet, String> {
    List<Pet> findByOwnerUserId(String ownerId);
    List<Pet> findAll();

    Pet findByPetID(String petId);

    @Query(value = "{ 'createdAt': { $gte: ?0, $lte: ?1 } }", count = true)
    Long countPetsByTimePeriod(Instant startDate, Instant endDate);
}
