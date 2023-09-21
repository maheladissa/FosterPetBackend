package com.fosterpet.backend.user;

import java.util.List;

public interface UserService {
    String save (User user);

    List<User> getUserFirstNameStartWith(String username);

    List<User> getAllUsers();

    User getUserById(String id);
}
