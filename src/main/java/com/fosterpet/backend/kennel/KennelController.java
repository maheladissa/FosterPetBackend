package com.fosterpet.backend.kennel;

import com.fosterpet.backend.common.PaymentRates;
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

    @GetMapping("/filter")
    public ResponseEntity<?> getKennelsNearAndAnimalType(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double maxDistance, @RequestParam String animalType){
        try {
            return ResponseEntity.ok(kennelService.filterKennels(longitude, latitude, maxDistance, animalType));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveKennels(){
        try {
            return ResponseEntity.ok(kennelService.getAllActiveKennels());
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

    @PostMapping("/update-rates")
    public ResponseEntity<?> updateRate(@RequestBody KennelPaymentRateRequest rate){
        try {
            return ResponseEntity.ok(kennelService.updatePaymentRate(rate));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getAllKennelsPendingApproval(){
        try {
            return ResponseEntity.ok(kennelService.getAllKennelsPendingApproval());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveKennel(@RequestParam String kennelId){
        try {
            return ResponseEntity.ok(kennelService.approveKennel(kennelId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectKennel(@RequestParam String kennelId){
        try {
            return ResponseEntity.ok(kennelService.rejectKennel(kennelId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteKennel(@RequestParam String kennelId){
        try {
            return ResponseEntity.ok(kennelService.deleteKennel(kennelId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
