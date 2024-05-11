package com.fosterpet.backend.review;

import java.util.List;

public interface ReviewService {
    ReviewResponse save(ReviewRequest reviewRequest);

    List<ReviewResponse> getAllReviews();

    List<ReviewResponse> getAllReviewsByUser(String userId);

    List<ReviewResponse> getAllReviewsByKennel(String kennelId);

    List<ReviewResponse> getAllReviewsByVolunteer(String volunteerId);

    Integer getAverageRatingByKennel(String kennelId);

    Integer getAverageRatingByVolunteer(String volunteerId);

}
