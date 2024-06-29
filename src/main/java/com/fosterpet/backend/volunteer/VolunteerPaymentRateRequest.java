package com.fosterpet.backend.volunteer;

import com.fosterpet.backend.common.PaymentRates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerPaymentRateRequest {
    private String volunteerId;
    private List<PaymentRates> paymentRates;
}
