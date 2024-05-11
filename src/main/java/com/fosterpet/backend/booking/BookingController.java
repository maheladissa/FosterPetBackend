package com.fosterpet.backend.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> save(@RequestBody BookingRequest request){
        return ResponseEntity.ok(bookingService.save(request));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingResponse>> getBookingsByOwnerId(@RequestParam String ownerId) {
        return ResponseEntity.ok(bookingService.getBookingsByOwner(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/bookingId")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable String bookingId){
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @GetMapping("/kennelId")
    public ResponseEntity<List<BookingResponse>> getBookingsByKennel(@RequestParam String kennelId){
        return ResponseEntity.ok(bookingService.getBookingsByKennel(kennelId));
    }

    @GetMapping("/petId")
    public ResponseEntity<List<BookingResponse>> getBookingsByPet(@RequestParam String petId){
        return ResponseEntity.ok(bookingService.getBookingsByPet(petId));
    }

    @PostMapping("/update")
    public ResponseEntity<BookingResponse> updateBooking(@RequestBody BookingRequest request){
        return ResponseEntity.ok(bookingService.updateBooking(request));
    }
}
