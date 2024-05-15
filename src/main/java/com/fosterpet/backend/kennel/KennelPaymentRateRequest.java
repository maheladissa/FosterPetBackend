package com.fosterpet.backend.kennel;

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
public class KennelPaymentRateRequest {
    private String kennelId;
    private List<PaymentRates> paymentRates;
}
