package com.fosterpet.backend.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.booking.Booking;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "review")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private String reviewId;
    @DBRef
    private User reviewer;
    @DBRef
    private Kennel kennel;
    @DBRef
    private Booking booking;
    private String message;
    private Integer rating;


}
