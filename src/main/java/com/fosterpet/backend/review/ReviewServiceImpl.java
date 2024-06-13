package com.fosterpet.backend.review;

import com.fosterpet.backend.booking.Booking;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.volunteer.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public ReviewResponse save(ReviewRequest reviewRequest){
        User reviewer = new User();
        reviewer.setUserId(reviewRequest.getReviewerId());

        Kennel kennel = new Kennel();
        kennel.setKennelID(reviewRequest.getKennelId());

        Booking booking = new Booking();
        booking.setBookingID(reviewRequest.getBookingId());

        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(reviewRequest.getVolunteerId());

        Review review = Review.builder()
                .reviewer(reviewer)
                .kennel(reviewRequest.getKennelId() != null ? kennel : null)
                .volunteer(reviewRequest.getVolunteerId() != null ? volunteer : null)
                .booking(booking)
                .rating(reviewRequest.getRating())
                .message(reviewRequest.getMessage())
                .createdAt(Instant.now())
                .build();
        var saved = reviewRepository.save(review);

        return buildReviewResponse(saved);

    }

    @Override
    public List<ReviewResponse> getAllReviews(){
        var reviews = reviewRepository.findAll();
        return buildReviewResponses(reviews);
    }

    @Override
    public List<ReviewResponse> getAllReviewsByUser(String userId){
        var reviews = reviewRepository.findByReviewerUserId(userId);
        return buildReviewResponses(reviews);
    }

    @Override
    public List<ReviewResponse> getAllReviewsByKennel(String kennelId){
        var reviews = reviewRepository.findByKennelKennelID(kennelId);
        return buildReviewResponses(reviews);
    }

    @Override
    public List<ReviewResponse> getAllReviewsByVolunteer(String volunteerId){
        var reviews = reviewRepository.findByVolunteerVolunteerId(volunteerId);
        return buildReviewResponses(reviews);
    }

    @Override
    public Integer getAverageRatingByKennel(String kennelId){
        var reviews = reviewRepository.findByKennelKennelID(kennelId);
        Integer sum = 0;
        for(Review review : reviews){
            sum += review.getRating();
        }
        return sum/reviews.size();
    }

    @Override
    public Integer getAverageRatingByVolunteer(String volunteerId){
        var reviews = reviewRepository.findByVolunteerVolunteerId(volunteerId);
        Integer sum = 0;
        for(Review review : reviews){
            sum += review.getRating();
        }
        return sum/reviews.size();
    }

    private ReviewResponse buildReviewResponse(Review review){
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .reviewerId(review.getReviewer().getUserId())
                .kennelId(review.getKennel() != null ? review.getKennel().getKennelID() : null)
                .volunteerId(review.getVolunteer() != null ? review.getVolunteer().getVolunteerId() : null)
                .bookingId(review.getBooking().getBookingID())
                .rating(review.getRating())
                .message(review.getMessage())
                .createdAt(review.getCreatedAt())
                .build();
    }

    private List<ReviewResponse> buildReviewResponses(List<Review> reviews){
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        reviews.forEach(review -> reviewResponses.add(buildReviewResponse(review)));
        return reviewResponses;
    }
}
