package com.fosterpet.backend.review;

import com.fosterpet.backend.booking.Booking;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.volunteer.Volunteer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    private Review review;
    private User reviewer;
    private Kennel kennel;
    private Volunteer volunteer;
    private Booking booking;
    private Instant createdAt;

    @BeforeEach
    void setUp() {
        reviewer = new User();
        kennel = new Kennel();
        volunteer = new Volunteer();
        booking = new Booking();
        createdAt = Instant.now();

        review = Review.builder()
                .reviewId("review123")
                .reviewer(reviewer)
                .kennel(kennel)
                .volunteer(volunteer)
                .booking(booking)
                .message("Great service!")
                .rating(5)
                .createdAt(createdAt)
                .build();
    }

    @Test
    void testReviewFields() {
        // Test getters
        assertEquals("review123", review.getReviewId());
        assertEquals(reviewer, review.getReviewer());
        assertEquals(kennel, review.getKennel());
        assertEquals(volunteer, review.getVolunteer());
        assertEquals(booking, review.getBooking());
        assertEquals("Great service!", review.getMessage());
        assertEquals(5, review.getRating());
        assertEquals(createdAt, review.getCreatedAt());

        // Test setters
        review.setMessage("Updated message");
        assertEquals("Updated message", review.getMessage());

        review.setRating(4);
        assertEquals(4, review.getRating());
    }
}
