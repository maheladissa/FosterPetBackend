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
    public String save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/name")
    public List<User> getUsersStartWith(@RequestParam String name){
        return userService.getUserFirstNameStartWith(name);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/id")
    public ResponseEntity<User> getUserById(@RequestParam String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
