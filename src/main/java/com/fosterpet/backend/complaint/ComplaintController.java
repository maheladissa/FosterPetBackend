package com.fosterpet.backend.complaint;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ComplaintRequest request){
        try{
            return ResponseEntity.ok(complaintService.save(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllComplaints(){
        try {
            return ResponseEntity.ok(complaintService.getAllComplaints());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllComplaintsByUser(@RequestParam String userId){
        try {
            return ResponseEntity.ok(complaintService.getAllComplaintsByUser(userId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/kennel")
    public ResponseEntity<?> getAllComplaintsByKennel(@RequestParam String kennelId){
        try {
            return ResponseEntity.ok(complaintService.getAllComplaintsByKennel(kennelId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/volunteer")
    public ResponseEntity<?> getAllComplaintsByVolunteer(@RequestParam String volunteerId){
        try {
            return ResponseEntity.ok(complaintService.getAllComplaintsByVolunteer(volunteerId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{complaintId}")
    public ResponseEntity<?> getComplaintById(@PathVariable String complaintId){
        try {
            return ResponseEntity.ok(complaintService.getComplaintById(complaintId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update/status")
    public ResponseEntity<?> updateComplaintStatus(@RequestBody ComplaintRequest request){
        try {
            return ResponseEntity.ok(complaintService.updateComplaintStatus(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update/remarks")
    public ResponseEntity<?> updateComplaintRemarks(@RequestBody ComplaintRequest request){
        try {
            return ResponseEntity.ok(complaintService.updateComplaintRemarks(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update/admin")
    public ResponseEntity<?> updateComplaintAdmin(@RequestBody ComplaintRequest request){
        try {
            return ResponseEntity.ok(complaintService.updateComplaintAdmin(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/booking")
    public ResponseEntity<?> getAllComplaintsByBooking(@RequestParam String bookingId){
        try {
            return ResponseEntity.ok(complaintService.getAllComplaintsByBooking(bookingId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getAllComplaintsByStatus(@RequestParam String status){
        try {
            return ResponseEntity.ok(complaintService.getAllComplaintsByStatus(status));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
