package com.fosterpet.backend.pet;

import java.util.List;

public interface PetService {
    PetResponse save(PetRequest request);

    List<PetResponse> getAllPets();

    List<PetResponse> getPetsByOwner(String ownerId);

    PetResponse getPetById(String petId);

    PetResponse updatePet(PetRequest request);

    PetResponse deletePet(String petId);
}