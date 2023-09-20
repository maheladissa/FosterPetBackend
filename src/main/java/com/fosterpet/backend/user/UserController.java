package com.fosterpet.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<User> getUsersStartWith(@RequestParam("name") String name){
        return userService.getUserFirstNameStartWith(name);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


}
