package com.fosterpet.backend.review;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> save(@RequestBody ReviewRequest request){
        return ResponseEntity.ok(reviewService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsByUser(@RequestParam String userId){
        return ResponseEntity.ok(reviewService.getAllReviewsByUser(userId));
    }

    @GetMapping("/kennel")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsByKennel(@RequestParam String kennelId){
        return ResponseEntity.ok(reviewService.getAllReviewsByKennel(kennelId));
    }

    @GetMapping("/kennel/average")
    public ResponseEntity<Integer> getAverageRatingByKennel(@RequestParam String kennelId){
        return ResponseEntity.ok(reviewService.getAverageRatingByKennel(kennelId));
    }

}
