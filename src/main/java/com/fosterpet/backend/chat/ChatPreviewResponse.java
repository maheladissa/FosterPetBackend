package com.fosterpet.backend.chat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatPreviewResponse {

    private String chatThreadId;
    private String chatThreadName;
    private ChatMessageResponse lastMessage;

}
