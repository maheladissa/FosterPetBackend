package com.fosterpet.backend.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody BookingRequest request){
        try {
            return ResponseEntity.ok(bookingService.save(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/owner")
    public ResponseEntity<?> getBookingsByOwnerId(@RequestParam String ownerId) {
        try {
            return ResponseEntity.ok(bookingService.getBookingsByOwner(ownerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings(){
        try {
            return ResponseEntity.ok(bookingService.getAllBookings());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bookingId")
    public ResponseEntity<?> getBookingById(@RequestParam String bookingId){
        try {
            return ResponseEntity.ok(bookingService.getBookingById(bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/kennelId")
    public ResponseEntity<?> getBookingsByKennel(@RequestParam String kennelId){
        try {
            return ResponseEntity.ok(bookingService.getBookingsByKennel(kennelId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/volunteerId")
    public ResponseEntity<?> getBookingsByVolunteer(@RequestParam String volunteerId){
        try {
            return ResponseEntity.ok(bookingService.getBookingsByVolunteer(volunteerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/petId")
    public ResponseEntity<?> getBookingsByPet(@RequestParam String petId){
        try {
            return ResponseEntity.ok(bookingService.getBookingsByPet(petId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmBooking(@RequestParam String bookingId){
        try {
            return ResponseEntity.ok(bookingService.confirmBooking(bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelBooking(@RequestParam String bookingId){
        try {
            return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeBooking(@RequestParam String bookingId){
        try {
            return ResponseEntity.ok(bookingService.completeBooking(bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectBooking(@RequestParam String bookingId){
        try {
            return ResponseEntity.ok(bookingService.rejectBooking(bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/ongoing")
    public ResponseEntity<?> ongoingBooking(@RequestParam String bookingId){
        try {
            return ResponseEntity.ok(bookingService.ongoingBooking(bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateBooking(@RequestBody BookingRequest request){
        try {
            return ResponseEntity.ok(bookingService.updateBooking(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getBookingsByStatus(@RequestParam String status){
        try {
            return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
