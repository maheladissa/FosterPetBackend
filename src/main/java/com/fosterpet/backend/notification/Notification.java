package com.fosterpet.backend.notification;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@Document(collection = "notifications")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private String notificationID;
    @DBRef
    private User sender;
    @DBRef
    private User receiver;
    private String heading;
    private String message;
    private NotificationType type;
    private Instant createdAt;
    private boolean isRead;


}
