package com.fosterpet.backend.session;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {
    Optional<Session> findBySessionId(String sessionId);
    Optional<Session> findByToken(String token);
    List<Session> findByUserId(String userId);

    void deleteBySessionId(String sessionId);
    void deleteByToken(String token);

    void deleteByExpirationBefore(Instant now);
}
