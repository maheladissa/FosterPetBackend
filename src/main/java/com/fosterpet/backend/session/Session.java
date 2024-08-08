package com.fosterpet.backend.session;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@Document(collection = "session")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private String sessionId;
    private String userId;
    private String token;
    private Instant expiration;
    private Long createdAt;
    private Boolean isActive;
    private String deviceType;
    private String os;
    private String expoDeviceToken;
    private Instant lastLogin;
}
