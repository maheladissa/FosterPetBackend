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
    public ResponseEntity<?> save(@ModelAttribute PetRequest request){
        try {
            return ResponseEntity.ok(petService.save(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/owner")
    public ResponseEntity<?> getPetsByOwnerId(@RequestParam String ownerId) {
        try {
            return ResponseEntity.ok(petService.getPetsByOwner(ownerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPets(){
        try {
            return ResponseEntity.ok(petService.getAllPets());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getPetById(@RequestParam String petId){
        try {
            return ResponseEntity.ok(petService.getPetById(petId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePet(@ModelAttribute PetRequest request){
        try {
            return ResponseEntity.ok(petService.updatePet(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deletePet(@RequestParam String petId){
        try {
            return ResponseEntity.ok(petService.deletePet(petId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
