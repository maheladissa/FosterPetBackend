package com.fosterpet.backend.notification;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String senderId;
    private String receiverId;
    private String heading;
    private String message;
    private NotificationType type;

}
