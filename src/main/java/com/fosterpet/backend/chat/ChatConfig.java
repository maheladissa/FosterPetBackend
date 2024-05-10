package com.fosterpet.backend.chat;

import com.azure.communication.chat.ChatClient;
import com.azure.communication.chat.ChatClientBuilder;
import com.azure.communication.common.CommunicationTokenCredential;
import com.azure.communication.common.CommunicationUserIdentifier;
import com.fosterpet.backend.common.AzureIdentityGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ChatConfig {

    private final AzureIdentityGenerator azureIdentityGenerator;

    @Value("${azure.communication.chat-endpoint}")
    private String endpoint;

    @Value("${azure.communication.chat.admin.user-id}")
    private String userID;

    @Bean
    public ChatClient createChatClient() {
        String userAccessToken = azureIdentityGenerator.accessToken(new CommunicationUserIdentifier(userID)).getToken();
        // Create a CommunicationTokenCredential with the given access token, which is only valid until the token is valid
        CommunicationTokenCredential userCredential = new CommunicationTokenCredential(userAccessToken);
        // Initialize the chat client
        final ChatClientBuilder builder = new ChatClientBuilder();
        builder.endpoint(endpoint)
                .credential(userCredential);
        ChatClient chatClient = builder.buildClient();
        return chatClient;
    }




}
