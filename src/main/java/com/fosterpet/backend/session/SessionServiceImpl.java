package com.fosterpet.backend.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public void createSession(String userId, String token,Instant expiration, String deviceType, String os, String expoDeviceToken) {
        Session session = Session.builder()
                .userId(userId)
                .token(token)
                .createdAt(System.currentTimeMillis())
                .expiration(expiration)
                .isActive(true)
                .deviceType(deviceType)
                .os(os)
                .lastLogin(Instant.now())
                .expoDeviceToken(expoDeviceToken)
                .build();
        sessionRepository.save(session);
    }

    @Override
    public void deleteSessionById(String sessionId) {
        sessionRepository.deleteBySessionId(sessionId);
    }

    @Override
    public void deleteSessionByToken(String token) {
        sessionRepository.deleteByToken(token);
    }

    @Override
    public Session validateSession(String token) {
        return sessionRepository.findByToken(token).orElse(null);
    }

    @Override
    public void invalidateSession(String token) {
        Session session = sessionRepository.findByToken(token).orElse(null);
        if (session != null) {
            session.setIsActive(false);
            sessionRepository.save(session);
        }
    }

    @Override
    public void deleteExpiredSessions() {
        sessionRepository.deleteByExpirationBefore(Instant.now());
    }

    @Override
    public List<Session> findByUserId(String userId) {
        return sessionRepository.findByUserId(userId);
    }

}
