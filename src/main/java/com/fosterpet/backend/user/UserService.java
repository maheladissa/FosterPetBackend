package com.fosterpet.backend.user;

import java.util.List;

public interface UserService {
    UserResponse save (UserRequest userRequest);

    List<UserResponse> getUserFirstNameStartWith(String username);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(String id);

    UserResponse update(UserRequest userRequest);
}
