package com.fosterpet.backend.volunteer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
@CrossOrigin
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @PostMapping
    public ResponseEntity<VolunteerResponse> save(@ModelAttribute VolunteerRequest request){
        return ResponseEntity.ok(volunteerService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<VolunteerResponse>> getAllVolunteers(){
        return ResponseEntity.ok(volunteerService.findAll());
    }

    @GetMapping("/id")
    public ResponseEntity<VolunteerResponse> getVolunteerById(@RequestParam String volunteerId){
        return ResponseEntity.ok(volunteerService.findById(volunteerId));
    }

    @GetMapping("/user")
    public ResponseEntity<VolunteerResponse> getVolunteerByUserId(@RequestParam String userId){
        return ResponseEntity.ok(volunteerService.findByUserId(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<VolunteerResponse> updateVolunteer(@ModelAttribute VolunteerRequest request){
        return ResponseEntity.ok(volunteerService.update(request));
    }

}
