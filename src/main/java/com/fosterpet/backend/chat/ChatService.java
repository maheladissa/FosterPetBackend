package com.fosterpet.backend.chat;

import java.util.List;

public interface ChatService {
    String createChatThread(String userId1, String userId2);

    List<String> getAllChatThreads();

    List<String> getChatThreadsByUser(String userId);

    String sendMessage(String chatThreadId, String senderId, String message, String attachment);

    List<String> getTreadParticipants(String chatThreadId);

    List<ChatMessageResponse> getMessages(String chatThreadId);

}
