package com.fosterpet.backend.volunteer;


import com.fosterpet.backend.kennel.KennelPaymentRateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/volunteer")
@CrossOrigin
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute VolunteerRequest request){
        try {
            return ResponseEntity.ok(volunteerService.save(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllVolunteers(){
        try {
            return ResponseEntity.ok(volunteerService.findAll());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getVolunteerById(@RequestParam String volunteerId){
        try {
            return ResponseEntity.ok(volunteerService.findById(volunteerId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveVolunteers(){
        try {
            return ResponseEntity.ok(volunteerService.getAllActiveVolunteers());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveVolunteer(@RequestParam String volunteerId){
        try {
            return ResponseEntity.ok(volunteerService.approveVolunteer(volunteerId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getVolunteerByUserId(@RequestParam String userId){
        try {
            return ResponseEntity.ok(volunteerService.findByUserId(userId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateVolunteer(@ModelAttribute VolunteerRequest request){
        try {
            return ResponseEntity.ok(volunteerService.update(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/location")
    public ResponseEntity<?> getVolunteersByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double maxDistance){
        try {
            return ResponseEntity.ok(volunteerService.getVolunteersNear(longitude, latitude, maxDistance));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update-rates")
    public ResponseEntity<?> updateRate(@RequestBody VolunteerPaymentRateRequest rate){
        try {
            return ResponseEntity.ok(volunteerService.updatePaymentRate(rate));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteVolunteer(@RequestParam String volunteerId){
        try {
            return ResponseEntity.ok(volunteerService.deleteVolunteer(volunteerId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getVolunteersNearAndAnimalType(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double maxDistance, @RequestParam String animalType){
        try {
            return ResponseEntity.ok(volunteerService.filterVolunteers(longitude, latitude, maxDistance, animalType));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectVolunteer(@RequestParam String volunteerId){
        try {
            return ResponseEntity.ok(volunteerService.rejectVolunteer(volunteerId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/time-period")
    public ResponseEntity<?> getVolunteersByTimePeriod(@RequestParam String startDate, @RequestParam String endDate){
        try {
            return ResponseEntity.ok(volunteerService.getVolunteerByTimePeriod(startDate, endDate));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
