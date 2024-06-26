package com.fosterpet.backend.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private String reviewId;
    private String review;
    private Integer rating;
    private String message;

    private String kennelId;

    private String reviewerId;

    private String volunteerId;

    private String bookingId;

    private Instant createdAt;
}
