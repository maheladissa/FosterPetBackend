package com.fosterpet.backend.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionServiceImpl sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSession_shouldSaveSession() {
        // Arrange
        String userId = "user123";
        String token = "token123";
        Instant expiration = Instant.now().plusSeconds(3600);
        String deviceType = "Mobile";
        String os = "Android";
        String expoDeviceToken = "expoToken123";

        // Act
        sessionService.createSession(userId, token, expiration, deviceType, os, expoDeviceToken);

        // Assert
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    void deleteSessionById_shouldDeleteSession() {
        // Arrange
        String sessionId = "session123";

        // Act
        sessionService.deleteSessionById(sessionId);

        // Assert
        verify(sessionRepository, times(1)).deleteBySessionId(sessionId);
    }

    @Test
    void deleteSessionByToken_shouldDeleteSession() {
        // Arrange
        String token = "token123";

        // Act
        sessionService.deleteSessionByToken(token);

        // Assert
        verify(sessionRepository, times(1)).deleteByToken(token);
    }

    @Test
    void validateSession_shouldReturnSessionIfExists() {
        // Arrange
        String token = "token123";
        Session session = new Session();
        when(sessionRepository.findByToken(token)).thenReturn(Optional.of(session));

        // Act
        Session result = sessionService.validateSession(token);

        // Assert
        assertNotNull(result);
        verify(sessionRepository, times(1)).findByToken(token);
    }

    @Test
    void validateSession_shouldReturnNullIfNotExists() {
        // Arrange
        String token = "token123";
        when(sessionRepository.findByToken(token)).thenReturn(Optional.empty());

        // Act
        Session result = sessionService.validateSession(token);

        // Assert
        assertNull(result);
        verify(sessionRepository, times(1)).findByToken(token);
    }

    @Test
    void invalidateSession_shouldDeactivateSessionIfExists() {
        // Arrange
        String token = "token123";
        Session session = new Session();
        when(sessionRepository.findByToken(token)).thenReturn(Optional.of(session));

        // Act
        sessionService.invalidateSession(token);

        // Assert
        assertFalse(session.getIsActive());
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void invalidateSession_shouldDoNothingIfNotExists() {
        // Arrange
        String token = "token123";
        when(sessionRepository.findByToken(token)).thenReturn(Optional.empty());

        // Act
        sessionService.invalidateSession(token);

        // Assert
        verify(sessionRepository, never()).save(any(Session.class));
    }

    @Test
    void deleteExpiredSessions_shouldDeleteSessionsBeforeNow() {
        // Act
        sessionService.deleteExpiredSessions();

        // Assert
        verify(sessionRepository, times(1)).deleteByExpirationBefore(any(Instant.class));
    }

    @Test
    void findByUserId_shouldReturnSessionsForUser() {
        // Arrange
        String userId = "user123";
        List<Session> sessions = List.of(new Session());
        when(sessionRepository.findByUserId(userId)).thenReturn(sessions);

        // Act
        List<Session> result = sessionService.findByUserId(userId);

        // Assert
        assertEquals(sessions, result);
        verify(sessionRepository, times(1)).findByUserId(userId);
    }
}
