package com.fosterpet.backend.notification;

import java.util.List;

public interface NotificationService {
    NotificationResponse save(NotificationRequest request);
    List<NotificationResponse> getNotificationsByReceiver(String userId);

}
