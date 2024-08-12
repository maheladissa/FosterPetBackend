package com.fosterpet.backend.user;

import java.time.Instant;
import java.util.List;

public interface UserService {
    UserResponse save (UserRequest userRequest);

    List<UserResponse> getUserFirstNameStartWith(String username);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(String id);

    UserResponse update(UserRequest userRequest);

    List<String> getExpoTokensByUserId(String userId);

    UserResponse deleteUser(String id);

    Long countUsersByTimePeriod(Instant startDate, Instant endDate);

    List<UserResponse> findUsersByTimePeriod(String startDate, String endDate);
}
