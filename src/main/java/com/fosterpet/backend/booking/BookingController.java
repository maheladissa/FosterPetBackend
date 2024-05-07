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
}
