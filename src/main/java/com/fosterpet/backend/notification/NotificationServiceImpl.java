package com.fosterpet.backend.notification;

import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ExpoNotificationService expoPushNotificationService;

    @Autowired
    private UserService userService;

    private NotificationResponse save(NotificationRequest request) {
        try{
            User sender = new User();
            sender.setUserId(request.getSenderId());
            User receiver = new User();
            receiver.setUserId(request.getReceiverId());

            Notification notification = Notification.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .heading(request.getHeading())
                    .message(request.getMessage())
                    .type(request.getType())
                    .createdAt(Instant.now())
                    .isRead(false)
                    .build();

            var saved = notificationRepository.save(notification);

            List<String> receiverList = userService.getExpoTokensByUserId(request.getReceiverId());

            ExpoNotification expoNotification = ExpoNotification.builder()
                    .to(receiverList)
                    .title(request.getHeading())
                    .body(request.getMessage())
                    .build();

            return null;



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NotificationResponse> getNotificationsByReceiver(String userId) {
        var notifications = notificationRepository.findByReceiverUserId(userId);
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationResponses.add(NotificationResponse.builder()
                                .notificationID(notification.getNotificationID())
                                .senderName(notification.getSender().getFirstName()+" "+notification.getSender().getLastName())
                                .receiverId(notification.getReceiver().getUserId())
                                .heading(notification.getHeading())
                                .message(notification.getMessage())
                                .type(notification.getType())
                                .createdAt(notification.getCreatedAt().toString())
                                .isRead(notification.isRead())
                                .build());
        }
        return notificationResponses;
    }

    @Override
    public NotificationResponse accountVerificationUserNotification(String receiverId) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Your account is verified!")
                .message("Thank you for verifying your email/phone number. You can now book pet care services.")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }
    @Override
    public NotificationResponse passwordResetNotification(String receiverId) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Password reset requested")
                .message("We received a request to reset your password. Please check your email for instructions.")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse bookingConfirmationUserNotification(String receiverId, String kennelName) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Booking confirmed")
                .message("Your booking with "+kennelName+" has been confirmed. See details in the app.")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse bookingUpdateUserNotification(String receiverId, String kennelName) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Booking updated")
                .message("Your booking with "+kennelName+" has been rescheduled/cancelled (details in app).")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse accountVerificationKennelNotification(String receiverId) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Your account is verified!")
                .message("Thank you for verifying your email/phone number. You can now accept bookings.")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse bookingRequestKennelNotification(String receiverId, String petName, String ownerName) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("New booking request")
                .message("You have a new booking request from "+ownerName+" for "+petName+".")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse bookingConfirmationKennelNotification(String receiverId, String petName, String ownerName) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Booking confirmed")
                .message("You have confirmed the booking for "+petName+" owned by "+ownerName+".")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse bookingUpdateKennelNotification(String receiverId, String petName, String ownerName) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Booking updated")
                .message("You have updated the booking for "+petName+" owned by "+ownerName+".")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse bookingReminderKennelNotification(String receiverId, String petName, String ownerName) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Booking reminder")
                .message("Reminder: You have a booking for "+petName+" owned by "+ownerName+" tomorrow.")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse paymentReceivedKennelNotification(String receiverId, String bookingId) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Payment received")
                .message("Payment for booking "+bookingId+" has been received.")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }

    @Override
    public NotificationResponse chatMessageNotification(String receiverId, String senderName, String message) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("New message from "+senderName)
                .message(message)
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }


}
