package com.fosterpet.backend.chat;

import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private String id;

    private String senderId;
    private String senderName;
    private String senderType;

    private String message;

    private String attachment;

    private String time;


}
