package com.fosterpet.backend.chat;

import java.util.List;

public interface ChatService {
    Chat createChatThread(String userId, String kennelId, String volunteerId);

    List<String> getAllChatThreads();

    List<String> getChatThreadsByUser(String userId);

    String sendMessage(String chatThreadId, String senderId, String senderType, String message, String attachment);

    List<String> getTreadParticipants(String chatThreadId);

    List<ChatMessageResponse> getMessages(String chatThreadId);

    List<ChatPreviewResponse> getChatPreviewByUser(String userId);

    List<ChatPreviewResponse> getChatPreviewByKennel(String kennelId);

    Chat getChatThreadByUserAndKennel(String userId, String kennelId);

}
