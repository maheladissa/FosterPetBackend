package com.fosterpet.backend.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequest {
    private String senderId;
    private String chatThreadId;
    private String message;
    private MultipartFile attachment;
}
