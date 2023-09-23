package com.fosterpet.backend.pet;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends MongoRepository <Pet, String> {
    List<Pet> findByPetNameStartsWith(String name);
    List<Pet> findByOwnerUserId(String ownerId);
    List<Pet> findAll();

}
