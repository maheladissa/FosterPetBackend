package com.fosterpet.backend.chat;

import com.azure.communication.chat.ChatClient;
import com.azure.communication.chat.ChatThreadClient;
import com.azure.communication.chat.models.*;
import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.core.http.rest.PagedIterable;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import com.fosterpet.backend.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public String createChatThread(String userId1, String userId2) {

        User user1 = userRepository.findByUserId(userId1);
        User user2 = userRepository.findByUserId(userId2);

        CommunicationUserIdentifier identity1 = new CommunicationUserIdentifier(user1.getAzureCommunicationId());
        CommunicationUserIdentifier identity2 = new CommunicationUserIdentifier(user2.getAzureCommunicationId());

        ChatParticipant firstThreadParticipant = new ChatParticipant()
                .setCommunicationIdentifier(identity1)
                .setDisplayName(userId1);

        ChatParticipant secondThreadParticipant = new ChatParticipant()
                .setCommunicationIdentifier(identity2)
                .setDisplayName(userId2);

        CreateChatThreadOptions createChatThreadOptions = new CreateChatThreadOptions("Topic")
                .addParticipant(firstThreadParticipant)
                .addParticipant(secondThreadParticipant);

        CreateChatThreadResult result = chatClient.createChatThread(createChatThreadOptions);
        String chatThreadId = result.getChatThread().getId();

        Chat chat = Chat.builder()
                .chatThreadId(chatThreadId)
                .user1(user1)
                .user2(user2)
                .build();

        chatRepository.save(chat);

        return chatThreadId;
    }

    @Override
    public List<String> getAllChatThreads() {
        PagedIterable<ChatThreadItem> chatThreads = chatClient.listChatThreads();
        ArrayList<String> chatThreadIds = new ArrayList<>();
        chatThreads.forEach(chatThread -> {
            chatThreadIds.add(chatThread.getId());
        });
        return chatThreadIds;
    }

    @Override
    public List<String> getChatThreadsByUser(String userId) {
        List<Chat> chats = chatRepository.findByUser1UserIdOrUser2UserId(userId, userId);
        ArrayList<String> chatThreadIds = new ArrayList<>();
        chats.forEach(chat -> {
            chatThreadIds.add(chat.getChatThreadId());
        });
        return chatThreadIds;
    }

    @Override
    public String sendMessage(String chatThreadId, String senderId, String message, String attachment) {
        ChatThreadClient chatThreadClient = chatClient.getChatThreadClient(chatThreadId);

        Map<String, String> metadata = new HashMap<String, String>();
        if(attachment != null) {
            metadata.put("hasAttachment", "true");
            metadata.put("attachmentUrl", attachment);
        }
        else {
            metadata.put("hasAttachment", "false");
        }

        SendChatMessageOptions sendChatMessageOptions = new SendChatMessageOptions()
                .setContent(message)
                .setType(ChatMessageType.TEXT)
                .setSenderDisplayName(senderId)
                .setMetadata(metadata);

        SendChatMessageResult sendChatMessageResult = chatThreadClient.sendMessage(sendChatMessageOptions);
        String chatMessageId = sendChatMessageResult.getId();

        return chatMessageId;
    }

    @Override
    public List<String> getTreadParticipants(String chatThreadId) {
        ChatThreadClient chatThreadClient = chatClient.getChatThreadClient(chatThreadId);
        PagedIterable<ChatParticipant> chatParticipantsResponse = chatThreadClient.listParticipants();
        ArrayList<String> chatParticipants = new ArrayList<>();
        chatParticipantsResponse.forEach(chatParticipant -> {
            chatParticipants.add(chatParticipant.getDisplayName());
        });
        return chatParticipants;
    }

    @Override
    public List<ChatMessageResponse> getMessages(String chatThreadId) {
        ChatThreadClient chatThreadClient = chatClient.getChatThreadClient(chatThreadId);
        PagedIterable<ChatMessage> chatMessages = chatThreadClient.listMessages();
        ArrayList<ChatMessageResponse> chatMessageResponses = new ArrayList<>();
        chatMessages.forEach(chatMessage -> {
            String attachmentUrl = (chatMessage.getMetadata() == null) ? null : chatMessage.getMetadata().get("attachmentUrl");
            String senderDisplayName = chatMessage.getSenderDisplayName();
            User sender = null;
            if (senderDisplayName != null) {
                sender = userRepository.findByUserId(senderDisplayName);
            }
            chatMessageResponses.add(ChatMessageResponse.builder()
                    .id(chatMessage.getId())
                    .sender((sender != null) ?
                            UserResponse.builder()
                                    .userId(sender.getUserId())
                                    .firstName(sender.getFirstName())
                                    .lastName(sender.getLastName())
                                    .build() :
                            null)
                    .message(chatMessage.getContent().getMessage())
                    .attachment(attachmentUrl)
                    .time(chatMessage.getCreatedOn().toString())
                    .build());
        });
        return chatMessageResponses;
    }


}
