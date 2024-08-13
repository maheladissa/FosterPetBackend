package com.fosterpet.backend.volunteer;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import com.fosterpet.backend.common.PaymentRates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerResponse {
    private String volunteerId;
    private String userId;

    private String volunteerName;
    private Address volunteerAddress;
    private Location volunteerLocation;

    private List<PaymentRates> paymentRates;

    private String nicNumber;
    private List<String> images;
    private String profileImage;

    private Boolean isActive;
    private Boolean isApproved;
    private Instant createdAt;
}
