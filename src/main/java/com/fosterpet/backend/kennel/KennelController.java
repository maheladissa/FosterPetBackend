package com.fosterpet.backend.kennel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kennel")
@CrossOrigin
public class KennelController {

    @Autowired
    private KennelService kennelService;

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute KennelRequest request){
        try {
            return ResponseEntity.ok(kennelService.save(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/name")
    public ResponseEntity<?> getKennelStartWith(@RequestParam("name") String name){
        try {
            return ResponseEntity.ok(kennelService.getKennelStartWith(name));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/owner")
    public ResponseEntity<?> getKennelsByOwnerId(@RequestParam String ownerId) {
        try {
            return ResponseEntity.ok(kennelService.getKennelsByOwner(ownerId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllKennels(){
        try {
            return ResponseEntity.ok(kennelService.getAllKennels());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/near")
    public ResponseEntity<?> getKennelsNear(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double maxDistance){
        try {
            return ResponseEntity.ok(kennelService.getKennelsNear(longitude, latitude, maxDistance));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateKennel(@ModelAttribute KennelRequest request){
        try {
            return ResponseEntity.ok(kennelService.update(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getKennelById(@RequestParam String kennelId){
        try {
            return ResponseEntity.ok(kennelService.getKennelById(kennelId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
