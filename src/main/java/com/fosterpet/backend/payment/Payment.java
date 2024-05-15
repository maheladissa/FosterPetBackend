package com.fosterpet.backend.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Double amount;
    private String currency;
    private String customerEmail;
    private String customerName;
    private String customerID;
}
