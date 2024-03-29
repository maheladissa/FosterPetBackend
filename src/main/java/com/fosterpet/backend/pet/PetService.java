package com.fosterpet.backend.pet;

import com.fosterpet.backend.user.User;

import java.util.List;

public interface PetService {
    PetResponse save(PetRequest request);

    List<PetResponse> getAllPets();

    List<PetResponse> getPetsByOwner(String ownerId);
}