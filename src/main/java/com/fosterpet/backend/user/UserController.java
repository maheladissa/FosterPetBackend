package com.fosterpet.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> save(@ModelAttribute UserRequest userRequest){
        return ResponseEntity.ok(userService.save(userRequest));
    }

    @GetMapping("/name")
    public ResponseEntity<List<UserResponse>> getUsersStartWith(@RequestParam String name){
        return ResponseEntity.ok(userService.getUserFirstNameStartWith(name));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/id")
    public ResponseEntity<UserResponse> getUserById(@RequestParam String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@ModelAttribute UserRequest userRequest){
        return ResponseEntity.ok(userService.update(userRequest));
    }

}
