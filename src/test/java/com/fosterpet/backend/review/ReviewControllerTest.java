package com.fosterpet.backend.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveReview_Success() {
        ReviewRequest request = new ReviewRequest();
        ReviewResponse response = new ReviewResponse();
        when(reviewService.save(any(ReviewRequest.class))).thenReturn(response);

        ResponseEntity<?> result = reviewController.save(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void saveReview_Failure() {
        ReviewRequest request = new ReviewRequest();
        when(reviewService.save(any(ReviewRequest.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = reviewController.save(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAllReviews_Success() {
        List<ReviewResponse> responseList = new ArrayList<>();
        when(reviewService.getAllReviews()).thenReturn(responseList);

        ResponseEntity<?> result = reviewController.getAllReviews();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getAllReviews_Failure() {
        when(reviewService.getAllReviews()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = reviewController.getAllReviews();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAllReviewsByUser_Success() {
        List<ReviewResponse> responseList = new ArrayList<>();
        when(reviewService.getAllReviewsByUser(anyString())).thenReturn(responseList);

        ResponseEntity<?> result = reviewController.getAllReviewsByUser("userId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getAllReviewsByUser_Failure() {
        when(reviewService.getAllReviewsByUser(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = reviewController.getAllReviewsByUser("userId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAllReviewsByKennel_Success() {
        List<ReviewResponse> responseList = new ArrayList<>();
        when(reviewService.getAllReviewsByKennel(anyString())).thenReturn(responseList);

        ResponseEntity<?> result = reviewController.getAllReviewsByKennel("kennelId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getAllReviewsByKennel_Failure() {
        when(reviewService.getAllReviewsByKennel(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = reviewController.getAllReviewsByKennel("kennelId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAllReviewsByVolunteer_Success() {
        List<ReviewResponse> responseList = new ArrayList<>();
        when(reviewService.getAllReviewsByVolunteer(anyString())).thenReturn(responseList);

        ResponseEntity<?> result = reviewController.getAllReviewsByVolunteer("volunteerId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getAllReviewsByVolunteer_Failure() {
        when(reviewService.getAllReviewsByVolunteer(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = reviewController.getAllReviewsByVolunteer("volunteerId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAverageRatingByKennel_Failure() {
        when(reviewService.getAverageRatingByKennel(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = reviewController.getAverageRatingByKennel("kennelId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAverageRatingByVolunteer_Failure() {
        when(reviewService.getAverageRatingByVolunteer(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = reviewController.getAverageRatingByVolunteer("volunteerId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }
}
