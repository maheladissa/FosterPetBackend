package com.fosterpet.backend.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private String type;
    private double[] coordinates;
}