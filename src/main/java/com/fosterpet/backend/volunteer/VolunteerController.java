package com.fosterpet.backend.volunteer;


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

    @PostMapping
    public ResponseEntity<?> getVolunteersByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double maxDistance){
        try {
            return ResponseEntity.ok(volunteerService.getVolunteersNear(longitude, latitude, maxDistance));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
