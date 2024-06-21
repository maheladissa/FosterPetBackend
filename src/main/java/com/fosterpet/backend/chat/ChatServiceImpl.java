package com.fosterpet.backend.chat;

import com.azure.communication.chat.ChatClient;
import com.azure.communication.chat.ChatThreadClient;
import com.azure.communication.chat.models.*;
import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.core.http.rest.PagedIterable;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.kennel.KennelRepository;
import com.fosterpet.backend.notification.NotificationService;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import com.fosterpet.backend.user.UserResponse;
import com.fosterpet.backend.volunteer.Volunteer;
import com.fosterpet.backend.volunteer.VolunteerRepository;
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
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private KennelRepository kennelRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public String createChatThread(String userId, String kennelId, String volunteerId) {

        User user1 = userRepository.findByUserId(userId);
        User user2;
        if (kennelId != "") {
            Kennel kennel = kennelRepository.findByKennelID(kennelId);
            user2 = kennel.getOwner();
        }
        else {
            Volunteer volunteer = volunteerRepository.findByVolunteerId(volunteerId);
            user2 = volunteer.getUser();
        }

        CommunicationUserIdentifier identity1 = new CommunicationUserIdentifier(user1.getAzureCommunicationId());
        CommunicationUserIdentifier identity2 = new CommunicationUserIdentifier(user2.getAzureCommunicationId());

        ChatParticipant firstThreadParticipant = new ChatParticipant()
                .setCommunicationIdentifier(identity1)
                .setDisplayName(userId);

        ChatParticipant secondThreadParticipant = new ChatParticipant()
                .setCommunicationIdentifier(identity2)
                .setDisplayName(kennelId != null ? kennelId : volunteerId);

        CreateChatThreadOptions createChatThreadOptions = new CreateChatThreadOptions("Topic")
                .addParticipant(firstThreadParticipant)
                .addParticipant(secondThreadParticipant);

        CreateChatThreadResult result = chatClient.createChatThread(createChatThreadOptions);
        String chatThreadId = result.getChatThread().getId();

        Chat chat = Chat.builder()
                .chatThreadId(chatThreadId)
                .user(user1)
                .kennel(kennelId != "" ? kennelRepository.findByKennelID(kennelId) : null)
                .volunteer(volunteerId != "" ? volunteerRepository.findByVolunteerId(volunteerId) : null)
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
        List<Chat> chats = chatRepository.findByUserUserId(userId);
        ArrayList<String> chatThreadIds = new ArrayList<>();
        chats.forEach(chat -> {
            chatThreadIds.add(chat.getChatThreadId());
        });
        return chatThreadIds;
    }

    @Override
    public String sendMessage(String chatThreadId, String senderId, String senderType, String message, String attachment) {
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
                .setSenderDisplayName(senderType+":"+senderId)
                .setMetadata(metadata);

        SendChatMessageResult sendChatMessageResult = chatThreadClient.sendMessage(sendChatMessageOptions);
        String chatMessageId = sendChatMessageResult.getId();

        //Send notification to the other user
        Chat chat = chatRepository.findByChatThreadId(chatThreadId);
        String receiverId;
        String senderName;
        if (senderType.equals("User")) {
            receiverId = chat.getKennel().getOwner().getUserId();
            senderName = chat.getUser().getFirstName() + " " + chat.getUser().getLastName();
        }
        else if(senderType.equals("Kennel")){
            receiverId = chat.getUser().getUserId();
            senderName = chat.getKennel().getKennelName();
        }
        else {
            receiverId = chat.getVolunteer().getUser().getUserId();
            senderName = chat.getVolunteer().getUser().getFirstName() + " " + chat.getVolunteer().getUser().getLastName();
        }

        notificationService.chatMessageNotification(receiverId, senderName, message);



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

            if (senderDisplayName != null) {
                // Split the string based on the colon separator
                String[] parts = senderDisplayName.split(":");

                String senderName;
                String senderId;
                String senderType;

                if (parts.length == 2) {
                    // Assign the parts to the respective variables
                    senderType = parts[0];
                    senderId = parts[1];

                    // Check the senderType and assign the sender object accordingly
                    if (senderType.equals("User")) {
                        User sender = userRepository.findByUserId(senderId);
                        senderName = sender.getFirstName() + " " + sender.getLastName();
                    } else if (senderType.equals("Kennel")) {
                        Kennel sender = kennelRepository.findByKennelID(senderId);
                        senderName = sender.getKennelName();
                    } else if (senderType.equals("Volunteer")) {
                        Volunteer sender = volunteerRepository.findByVolunteerId(senderId);
                        senderName = sender.getUser().getFirstName() + " " + sender.getUser().getLastName();
                    } else {
                        // Handle the case where the senderType is not recognized
                        throw new IllegalArgumentException("Invalid senderType");
                    }

                    // Now you have senderType and senderId extracted from senderDisplayName
                } else {
                    // Handle the case where the format is unexpected
                    throw new IllegalArgumentException("Invalid senderDisplayName format");
                }



                chatMessageResponses.add(ChatMessageResponse.builder()
                        .id(chatMessage.getId())
                        .message(chatMessage.getContent().getMessage())
                        .senderName(senderName)
                        .senderId(senderId)
                        .senderType(senderType)
                        .attachment(attachmentUrl)
                        .time(chatMessage.getCreatedOn().toString())
                        .build());
            }
            });
        return chatMessageResponses;
    }

    @Override
    public List<ChatPreviewResponse> getChatPreviewByUser(String userId) {
        System.out.println(userId);
        List<Chat> chats = chatRepository.findByUserUserId(userId);
        ArrayList<ChatPreviewResponse> chatPreviewResponses = new ArrayList<>();
        chats.forEach(chat -> {
            ChatThreadClient chatThreadClient = chatClient.getChatThreadClient(chat.getChatThreadId());
            PagedIterable<ChatMessage> chatMessages = chatThreadClient.listMessages();
            ChatMessage lastMessage = chatMessages.iterator().next();
            String attachmentUrl = (lastMessage.getMetadata() == null) ? null : lastMessage.getMetadata().get("attachmentUrl");
            String senderDisplayName = lastMessage.getSenderDisplayName();
            // Split the string based on the colon separator
            String[] parts = senderDisplayName.split(":");

            String senderName;
            String senderId;
            String senderType;

            if (parts.length == 2) {
                // Assign the parts to the respective variables
                senderType = parts[0];
                senderId = parts[1];

                // Check the senderType and assign the sender object accordingly
                if (senderType.equals("User")) {
                    User sender = userRepository.findByUserId(senderId);
                    senderName = sender.getFirstName() + " " + sender.getLastName();
                } else if (senderType.equals("Kennel")) {
                    Kennel sender = kennelRepository.findByKennelID(senderId);
                    senderName = sender.getKennelName();
                } else if (senderType.equals("Volunteer")) {
                    Volunteer sender = volunteerRepository.findByVolunteerId(senderId);
                    senderName = sender.getUser().getFirstName() + " " + sender.getUser().getLastName();
                } else {
                    // Handle the case where the senderType is not recognized
                    throw new IllegalArgumentException("Invalid senderType");
                }

                // Now you have senderType and senderId extracted from senderDisplayName
            } else {
                // Handle the case where the format is unexpected
                throw new IllegalArgumentException("Invalid senderDisplayName format");
            }
            chatPreviewResponses.add(ChatPreviewResponse.builder()
                    .chatThreadId(chat.getChatThreadId())
                    .chatThreadName(chat.getKennel() != null ?
                            chat.getKennel().getKennelName() :
                            chat.getVolunteer().getUser().getFirstName() + " " + chat.getVolunteer().getUser().getLastName())

                    .lastMessage(ChatMessageResponse.builder()
                            .id(lastMessage.getId())
                            .senderName(senderName)
                            .senderId(senderId)
                            .message(lastMessage.getContent().getMessage())
                            .attachment(attachmentUrl)
                            .time(lastMessage.getCreatedOn().toString())
                            .build())
                    .build());
        });
        return chatPreviewResponses;
    }


}
