package com.fosterpet.backend.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Address {
    private String address1;
    private String address2;
    private String city;
    private Integer zipCode;
}

