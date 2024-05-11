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
        try{
            return ResponseEntity.ok(complaintService.save(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints(){
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/user")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaintsByUser(@RequestParam String userId){
        try {
            return ResponseEntity.ok(complaintService.getAllComplaintsByUser(userId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/kennel")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaintsByKennel(@RequestParam String kennelId){
        return ResponseEntity.ok(complaintService.getAllComplaintsByKennel(kennelId));
    }

    @GetMapping("/volunteer")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaintsByVolunteer(@RequestParam String volunteerId){
        return ResponseEntity.ok(complaintService.getAllComplaintsByVolunteer(volunteerId));
    }

    @GetMapping("/{complaintId}")
    public ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable String complaintId){
        return ResponseEntity.ok(complaintService.getComplaintById(complaintId));
    }

    @PostMapping("/update/status")
    public ResponseEntity<ComplaintResponse> updateComplaintStatus(@RequestBody ComplaintRequest request){
        return ResponseEntity.ok(complaintService.updateComplaintStatus(request));
    }

    @PostMapping("/update/remarks")
    public ResponseEntity<ComplaintResponse> updateComplaintRemarks(@RequestBody ComplaintRequest request){
        return ResponseEntity.ok(complaintService.updateComplaintRemarks(request));
    }

    @PostMapping("/update/admin")
    public ResponseEntity<ComplaintResponse> updateComplaintAdmin(@RequestBody ComplaintRequest request){
        return ResponseEntity.ok(complaintService.updateComplaintAdmin(request));
    }





    @GetMapping("/booking")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaintsByBooking(@RequestParam String bookingId){
        return ResponseEntity.ok(complaintService.getAllComplaintsByBooking(bookingId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaintsByStatus(@RequestParam String status){
        return ResponseEntity.ok(complaintService.getAllComplaintsByStatus(status));
    }

}
