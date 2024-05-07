package com.fosterpet.backend.notification;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findAll();
    List<Notification> findByReceiverUserId(String receiverId);
    List<Notification> findByCreatedAt(String createdDate);
}
