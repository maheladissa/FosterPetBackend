package com.fosterpet.backend.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.volunteer.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "chat")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    private String id;
    @DBRef
    private User user;
    @DBRef
    private Kennel kennel;
    @DBRef
    private Volunteer volunteer;


    private String chatThreadId;
}
