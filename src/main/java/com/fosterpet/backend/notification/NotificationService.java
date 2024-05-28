package com.fosterpet.backend.notification;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getNotificationsByReceiver(String userId);

    NotificationResponse sendAccountVerificationNotification(String receiverId);

}
