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
    public ResponseEntity<?> save(@ModelAttribute UserRequest userRequest){
        try {
            return ResponseEntity.ok(userService.save(userRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/name")
    public ResponseEntity<?> getUsersStartWith(@RequestParam String name){
        try {
            return ResponseEntity.ok(userService.getUserFirstNameStartWith(name));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getUserById(@RequestParam String id){
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@ModelAttribute UserRequest userRequest){
        try {
            return ResponseEntity.ok(userService.update(userRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String id){
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/time-period")
    public ResponseEntity<?> getUsersByTimePeriod(@RequestParam String startDate, @RequestParam String endDate){
        try {
            return ResponseEntity.ok(userService.findUsersByTimePeriod(startDate, endDate));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
