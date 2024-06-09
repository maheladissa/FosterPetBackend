package com.fosterpet.backend.complaint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintResponse {
    private String complaintId;
    private String message;
    private String status;

    private String kennelId;
    private String kennelName;

    private String volunteerId;
    private String volunteerName;

    private String userId;
    private String userName;

    private String bookingId;

    private String adminID;
    private String remarks;

    private Instant createdAt;
}
