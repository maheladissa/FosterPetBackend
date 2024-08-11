package com.fosterpet.backend.chat;

import java.util.List;

public interface ChatService {
    String createChatThread(String userId, String kennelId, String volunteerId);

    List<String> getAllChatThreads();

    List<String> getChatThreadsByUser(String userId);

    String sendMessage(String chatThreadId, String senderId, String senderType, String message, String attachment);

    List<String> getTreadParticipants(String chatThreadId);

    List<ChatMessageResponse> getMessages(String chatThreadId);

    List<ChatPreviewResponse> getChatPreviewByUser(String userId);

    String getChatThreadByUserAndKennel(String userId, String kennelId);

}
