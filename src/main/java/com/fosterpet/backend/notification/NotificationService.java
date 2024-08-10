package com.fosterpet.backend.notification;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getNotificationsByReceiver(String userId);

    //User
    NotificationResponse accountVerificationUserNotification(String receiverId);
    NotificationResponse passwordResetNotification(String receiverId);
    NotificationResponse bookingConfirmationUserNotification(String receiverId, String kennelName);
    NotificationResponse bookingUpdateUserNotification(String receiverId, String kennelName);

    //Kennel
    NotificationResponse accountVerificationKennelNotification(String receiverId);
    NotificationResponse bookingRequestKennelNotification(String receiverId, String petName, String ownerName);
    NotificationResponse bookingConfirmationKennelNotification(String receiverId, String petName, String ownerName);
    NotificationResponse bookingUpdateKennelNotification(String receiverId, String petName, String ownerName);
    NotificationResponse bookingReminderKennelNotification(String receiverId, String petName, String ownerName);
    NotificationResponse paymentReceivedKennelNotification(String receiverId, String bookingId);


    //Chat
    NotificationResponse chatMessageNotification(String receiverId, String senderName, String message);


    //System
    NotificationResponse sendPushNotification(String receiverId, String title, String message);


}
