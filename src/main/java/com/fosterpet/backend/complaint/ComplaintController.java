package com.fosterpet.backend.complaint;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintResponse> save(@RequestBody ComplaintRequest request){
        return ResponseEntity.ok(complaintService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints(){
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/user")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaintsByUser(@RequestParam String userId){
        return ResponseEntity.ok(complaintService.getAllComplaintsByUser(userId));
    }

    @GetMapping("/kennel")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaintsByKennel(@RequestParam String kennelId){
        return ResponseEntity.ok(complaintService.getAllComplaintsByKennel(kennelId));
    }

}
