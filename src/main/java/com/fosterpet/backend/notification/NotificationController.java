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

    @PostMapping
    public ResponseEntity<NotificationResponse> save(@RequestBody NotificationRequest request){
        return ResponseEntity.ok(notificationService.save(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByReceiver(@PathVariable String userId){
        return ResponseEntity.ok(notificationService.getNotificationsByReceiver(userId));
    }

}
