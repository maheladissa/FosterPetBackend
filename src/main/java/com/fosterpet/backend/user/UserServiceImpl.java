package com.fosterpet.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String save(User user){
        return userRepository.save(user).getUserID();
    }

    @Override
    public List<User> getUserFirstNameStartWith(String name){
        return userRepository.findByFirstNameStartsWith(name);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
