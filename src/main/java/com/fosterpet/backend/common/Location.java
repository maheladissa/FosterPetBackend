package com.fosterpet.backend.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Location {
    private String type;
    private double[] coordinates;
}