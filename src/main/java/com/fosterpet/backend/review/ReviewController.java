package com.fosterpet.backend.review;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ReviewRequest request){
        try {
            return ResponseEntity.ok(reviewService.save(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllReviews(){
        try {
            return ResponseEntity.ok(reviewService.getAllReviews());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllReviewsByUser(@RequestParam String userId){
        try {
            return ResponseEntity.ok(reviewService.getAllReviewsByUser(userId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/kennel")
    public ResponseEntity<?> getAllReviewsByKennel(@RequestParam String kennelId){
        try {
            return ResponseEntity.ok(reviewService.getAllReviewsByKennel(kennelId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/volunteer")
    public ResponseEntity<?> getAllReviewsByVolunteer(@RequestParam String volunteerId){
        try {
            return ResponseEntity.ok(reviewService.getAllReviewsByVolunteer(volunteerId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/kennel/average")
    public ResponseEntity<?> getAverageRatingByKennel(@RequestParam String kennelId){
        try {
            return ResponseEntity.ok(reviewService.getAverageRatingByKennel(kennelId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/volunteer/average")
    public ResponseEntity<?> getAverageRatingByVolunteer(@RequestParam String volunteerId){
        try {
            return ResponseEntity.ok(reviewService.getAverageRatingByVolunteer(volunteerId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
