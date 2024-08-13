package com.fosterpet.backend.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionTest {
    private Session session;
    private final String sessionId = "testSessionId";
    private final String userId = "testUserId";
    private final String token = "test";
    private final String expiration = "2021-08-01T00:00:00Z";
    private final Long createdAt = 1627804800L;
    private final Boolean isActive = true;
    private final String deviceType = "testDeviceType";
    private final String os = "testOs";
    private final String expoDeviceToken = "test";
    private final String lastLogin = "2021-08-01T00:00:00Z";

    @BeforeEach
    void setUp() {
        session = Session.builder()
                .sessionId(sessionId)
                .userId(userId)
                .token(token)
                .expiration(Instant.parse(expiration))
                .createdAt(createdAt)
                .isActive(isActive)
                .deviceType(deviceType)
                .os(os)
                .expoDeviceToken(expoDeviceToken)
                .lastLogin(Instant.parse(lastLogin))
                .build();
    }

    @Test
    void testSessionDetails() {
        assertEquals(sessionId, session.getSessionId());
        assertEquals(userId, session.getUserId());
        assertEquals(token, session.getToken());
        assertEquals(Instant.parse(expiration), session.getExpiration());
        assertEquals(createdAt, session.getCreatedAt());
        assertEquals(isActive, session.getIsActive());
        assertEquals(deviceType, session.getDeviceType());
        assertEquals(os, session.getOs());
        assertEquals(expoDeviceToken, session.getExpoDeviceToken());
        assertEquals(Instant.parse(lastLogin), session.getLastLogin());
    }

}
