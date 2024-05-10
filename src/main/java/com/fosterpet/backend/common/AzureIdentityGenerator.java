package com.fosterpet.backend.common;

import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;
import com.azure.communication.identity.models.CommunicationTokenScope;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.AzureKeyCredential;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.CommunicationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AzureIdentityGenerator {
    // You can find your endpoint and access key from your resource in the Azure portal
    @Value("${azure.communication.chat-endpoint}")
    String endpoint;

    @Value("${azure.communication.chat.access-key}")
    String accessKey;

    private CommunicationIdentityClient identityClient;

    @PostConstruct
    public void Init() throws CommunicationException {
        identityClient = new CommunicationIdentityClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(accessKey))
                .buildClient();
    }

    public CommunicationUserIdentifier createUser(){
        CommunicationUserIdentifier user = identityClient.createUser();
        return user; // Return the created user ID
    }


    public AccessToken accessToken(CommunicationUserIdentifier user) {
        // Issue an access token with a validity of 24 hours and the "voip" scope for a user identity
        List<CommunicationTokenScope> scopes = new ArrayList<>(Arrays.asList(CommunicationTokenScope.CHAT));
        AccessToken accessToken = identityClient.getToken(user, scopes);
        return accessToken;
    }
}
