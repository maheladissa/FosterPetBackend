package com.fosterpet.backend.pet;

import java.util.List;

public interface PetService {
    String save (Pet pet);

    List<Pet> getPetStartWith(String name);

    List<Pet> getAllPets();
}