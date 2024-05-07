package com.fosterpet.backend.notification;

import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public NotificationResponse save(NotificationRequest request) {
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

            return NotificationResponse.builder()
                    .notificationID(saved.getNotificationID())
                    .senderName(saved.getSender().getFirstName()+" "+saved.getSender().getLastName())
                    .receiverId(saved.getReceiver().getUserId())
                    .heading(saved.getHeading())
                    .message(saved.getMessage())
                    .type(saved.getType())
                    .createdAt(saved.getCreatedAt().toString())
                    .isRead(saved.isRead())
                    .build();

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
}
