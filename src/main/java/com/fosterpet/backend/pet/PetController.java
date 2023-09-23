package com.fosterpet.backend.pet;

import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import com.fosterpet.backend.user.UserService;
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
    public ResponseEntity<PetResponse> save(@RequestBody PetRequest request){
        return ResponseEntity.ok(petService.save(request));
    }

    @GetMapping("/name")
    public ResponseEntity<List<PetResponse>> getPetStartWith(@RequestParam("name") String name){
        return ResponseEntity.ok(petService.getPetStartWith(name));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<PetResponse>> getPetsByOwnerId(@RequestParam String ownerId) {
        return ResponseEntity.ok(petService.getPetsByOwner(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> getAllPets(){
        return ResponseEntity.ok(petService.getAllPets());
    }
}
