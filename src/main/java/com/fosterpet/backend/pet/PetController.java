package com.fosterpet.backend.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<PetResponse> save(@ModelAttribute PetRequest request){
        return ResponseEntity.ok(petService.save(request));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<PetResponse>> getPetsByOwnerId(@RequestParam String ownerId) {
        return ResponseEntity.ok(petService.getPetsByOwner(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> getAllPets(){
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/id")
    public ResponseEntity<PetResponse> getPetById(@RequestParam String petId){
        return ResponseEntity.ok(petService.getPetById(petId));
    }

    @PostMapping("/update")
    public ResponseEntity<PetResponse> updatePet(@ModelAttribute PetRequest request){
        return ResponseEntity.ok(petService.updatePet(request));
    }
}
