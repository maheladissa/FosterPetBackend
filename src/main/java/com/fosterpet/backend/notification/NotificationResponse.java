package com.fosterpet.backend.notification;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private String notificationID;

    private String senderName;


    private String receiverId;

    private String heading;
    private String message;
    private NotificationType type;
    private String createdAt;
    private boolean isRead;

}
