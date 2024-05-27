package com.fosterpet.backend.user;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private ImageMetadata profileImage;
    private Address address;
    private Instant createdAt;
}
