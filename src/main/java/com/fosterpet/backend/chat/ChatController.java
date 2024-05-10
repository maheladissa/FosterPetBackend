package com.fosterpet.backend.chat;

import com.fosterpet.backend.imagemetadata.ImageMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.CommunicationException;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ImageMetadataService imageMetadataService;

    @GetMapping("/create-chat-thread")
    public ResponseEntity<String> createChatThread(@RequestParam String userId1, @RequestParam String userId2) throws CommunicationException {
        return ResponseEntity.ok(chatService.createChatThread(userId1, userId2));
    }

    @GetMapping("/get-chat-threads")
    public ResponseEntity<List<String>> getChatThread() {
        return ResponseEntity.ok(chatService.getAllChatThreads());
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@ModelAttribute ChatMessageRequest chatMessageRequest) throws Exception {
        String attachmentUrl = null;
        if(chatMessageRequest.getAttachment() != null && !chatMessageRequest.getAttachment().isEmpty()){
            attachmentUrl = imageMetadataService.save(chatMessageRequest.getAttachment()).getImageUrl();
        }
        return ResponseEntity.ok(chatService.sendMessage(chatMessageRequest.getChatThreadId(), chatMessageRequest.getSenderId(), chatMessageRequest.getMessage(), attachmentUrl));
    }

    @GetMapping("/get-participants")
    public ResponseEntity<List<String>> getChatParticipants(@RequestParam String chatThreadId){
        return ResponseEntity.ok(chatService.getTreadParticipants(chatThreadId));
    }

    @GetMapping("/get-messages")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@RequestParam String chatThreadId){
        return ResponseEntity.ok(chatService.getMessages(chatThreadId));
    }

    @GetMapping("/get-chat-threads-by-user")
    public ResponseEntity<List<String>> getChatThreadsByUser(@RequestParam String userId){
        return ResponseEntity.ok(chatService.getChatThreadsByUser(userId));
    }

}
