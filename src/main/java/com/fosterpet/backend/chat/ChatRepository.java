package com.fosterpet.backend.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String>{
    List<Chat> findByUserUserId(String userId);
}
