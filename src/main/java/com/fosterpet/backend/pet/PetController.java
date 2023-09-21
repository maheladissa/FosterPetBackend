package com.fosterpet.backend.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public String save(@RequestBody Pet pet){
        return petService.save(pet);
    }

    @GetMapping
    public List<Pet> getPetStartWith(@RequestParam("name") String name){
        return petService.getPetStartWith(name);
    }

    @GetMapping("/all")
    public List<Pet> getAllPets(){
        return petService.getAllPets();
    }
}
