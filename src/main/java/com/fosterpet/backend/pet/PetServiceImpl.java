package com.fosterpet.backend.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public String save(Pet pet){
        return petRepository.save(pet).getPetID();
    }

    @Override
    public List<Pet> getPetStartWith(String name){
        return petRepository.findByPetNameStartsWith(name);
    }

    @Override
    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }
}
