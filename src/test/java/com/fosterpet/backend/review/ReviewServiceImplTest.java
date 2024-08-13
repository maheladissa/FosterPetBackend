package com.fosterpet.backend.review;

import com.fosterpet.backend.booking.Booking;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.volunteer.Volunteer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldSaveReviewAndReturnResponse() {
        // Arrange
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReviewerId("user123");
        reviewRequest.setKennelId("kennel123");
        reviewRequest.setBookingId("booking123");
        reviewRequest.setVolunteerId("volunteer123");
        reviewRequest.setRating(5);
        reviewRequest.setMessage("Great service!");

        User reviewer = new User();
        reviewer.setUserId("user123");

        Kennel kennel = new Kennel();
        kennel.setKennelID("kennel123");

        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId("volunteer123");

        Booking booking = new Booking();
        booking.setBookingID("booking123");

        Review savedReview = Review.builder()
                .reviewId("review123")
                .reviewer(reviewer)
                .kennel(kennel)
                .volunteer(volunteer)
                .booking(booking)
                .rating(5)
                .message("Great service!")
                .createdAt(Instant.now())
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // Act
        ReviewResponse response = reviewService.save(reviewRequest);

        // Assert
        assertNotNull(response);
        assertEquals("review123", response.getReviewId());
        assertEquals("user123", response.getReviewerId());
        assertEquals("kennel123", response.getKennelId());
        assertEquals("volunteer123", response.getVolunteerId());
        assertEquals("booking123", response.getBookingId());
        assertEquals(5, response.getRating());
        assertEquals("Great service!", response.getMessage());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }


    @Test
    void getAverageRatingByKennel_shouldReturnAverageRating() {
        // Arrange
        String kennelId = "kennel123";
        Review review1 = Review.builder().rating(4).build();
        Review review2 = Review.builder().rating(6).build();
        when(reviewRepository.findByKennelKennelID(kennelId)).thenReturn(List.of(review1, review2));

        // Act
        Integer averageRating = reviewService.getAverageRatingByKennel(kennelId);

        // Assert
        assertNotNull(averageRating);
        assertEquals(5, averageRating); // (4 + 6) / 2
        verify(reviewRepository, times(1)).findByKennelKennelID(kennelId);
    }

    @Test
    void getAverageRatingByVolunteer_shouldReturnAverageRating() {
        // Arrange
        String volunteerId = "volunteer123";
        Review review1 = Review.builder().rating(3).build();
        Review review2 = Review.builder().rating(9).build();
        when(reviewRepository.findByVolunteerVolunteerId(volunteerId)).thenReturn(List.of(review1, review2));

        // Act
        Integer averageRating = reviewService.getAverageRatingByVolunteer(volunteerId);

        // Assert
        assertNotNull(averageRating);
        assertEquals(6, averageRating); // (3 + 9) / 2
        verify(reviewRepository, times(1)).findByVolunteerVolunteerId(volunteerId);
    }

    // Additional tests can be added here as needed
}
