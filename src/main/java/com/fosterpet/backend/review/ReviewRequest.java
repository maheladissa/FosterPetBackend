package com.fosterpet.backend.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private String reviewId;
    private Integer rating;
    private String message;
    private String kennelId;
    private String volunteerId;
    private String reviewerId;
    private String bookingId;
}
