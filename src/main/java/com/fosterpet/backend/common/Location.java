package com.fosterpet.backend.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private String longitude;
    private String latitude;
}
