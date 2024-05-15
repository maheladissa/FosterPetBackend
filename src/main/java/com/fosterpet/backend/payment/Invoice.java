package com.fosterpet.backend.payment;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@Document(collection = "invoice")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    private String invoiceId;
    @DBRef
    private String bookingId;
    @DBRef
    private String userId;
    private String paymentIntentId;
    private String status;
    private String paymentMethod;
    private String paymentMethodBrand;
    private String paymentMethodLast4;
    private String paymentMethodExpMonth;
    private String paymentMethodExpYear;
    private Long amount;
    private String currency;
    private Instant createdAt;
    private Instant paymentDate;
}
