package com.fosterpet.backend.session;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.List;

public interface SessionService {
    void createSession(String userId, String token, Instant expiration, String deviceType, String os, String expoDeviceToken);

    void deleteSessionById(String sessionId);

    void deleteSessionByToken(String token);

    Session validateSession(String token);

    void invalidateSession(String token);

    List<Session> findByUserId(String userId);

    @Scheduled(fixedRate = 60000)
    void deleteExpiredSessions();

}
