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
    public NotificationResponse sendAccountVerificationNotification(String receiverId) {
        NotificationRequest request = NotificationRequest.builder()
                .senderId("000000000000000000000000")
                .receiverId(receiverId)
                .heading("Your account is verified!")
                .message("Thank you for verifying your email/phone number. You can now book pet care services.")
                .type(NotificationType.SYSTEM)
                .build();
        return save(request);
    }
}
