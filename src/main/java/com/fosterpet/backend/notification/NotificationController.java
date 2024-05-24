package com.fosterpet.backend.notification;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ExpoNotificationService expoPushNotificationService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody NotificationRequest request){
        try {
            return ResponseEntity.ok(notificationService.save(request));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getNotificationsByReceiver(@PathVariable String userId){
        try {
            return ResponseEntity.ok(notificationService.getNotificationsByReceiver(userId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/expoNotification")
    public ResponseEntity<?> sendExpoNotification(@RequestBody ExpoNotification expoNotification){
        try {
            return ResponseEntity.ok(expoPushNotificationService.sendExpoNotification(expoNotification.getTo(), expoNotification.getTitle(), expoNotification.getBody()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
