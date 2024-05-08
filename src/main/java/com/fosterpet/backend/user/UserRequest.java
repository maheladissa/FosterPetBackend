package com.fosterpet.backend.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String userAddress1;
    private String userAddress2;
    private String userCity;
    private String userZip;
    private Role role;
    private Boolean isEmailVerified;
    private MultipartFile profileImage;
}
