package com.fosterpet.backend.chat;

import com.fosterpet.backend.imagemetadata.ImageMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ImageMetadataService imageMetadataService;

    @GetMapping("/create-chat-thread")
    public ResponseEntity<?> createChatThread(@RequestParam String userId, @RequestParam String kennelId, @RequestParam String volunteerId) {
        try {
            System.out.println("userId: " + userId);
            System.out.println("kennelId: " + kennelId);
            System.out.println("volunteerId: " + volunteerId);
            return ResponseEntity.ok(chatService.createChatThread(userId, kennelId, volunteerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-chat-threads")
    public ResponseEntity<?> getChatThread() {
        try {
            return ResponseEntity.ok(chatService.getAllChatThreads());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-chat-thread-by-user-and-kennel")
    public ResponseEntity<?> getChatThreadByUserAndKennel(@RequestParam String userId, @RequestParam String kennelId){
        try {
            return ResponseEntity.ok(chatService.getChatThreadByUserAndKennel(userId, kennelId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@ModelAttribute ChatMessageRequest chatMessageRequest) {
        try {
            String attachmentUrl = null;
            if(chatMessageRequest.getAttachment() != null && !chatMessageRequest.getAttachment().isEmpty()){
                attachmentUrl = imageMetadataService.save(chatMessageRequest.getAttachment()).getImageUrl();
            }
            return ResponseEntity.ok(chatService.sendMessage(chatMessageRequest.getChatThreadId(), chatMessageRequest.getSenderId(), chatMessageRequest.getSenderType() , chatMessageRequest.getMessage(), attachmentUrl));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get-participants")
    public ResponseEntity<?> getChatParticipants(@RequestParam String chatThreadId){
        try {
            return ResponseEntity.ok(chatService.getTreadParticipants(chatThreadId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-messages")
    public ResponseEntity<?> getChatMessages(@RequestParam String chatThreadId){
        try {
            return ResponseEntity.ok(chatService.getMessages(chatThreadId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-chat-threads-by-user")
    public ResponseEntity<?> getChatThreadsByUser(@RequestParam String userId){
        try {
            return ResponseEntity.ok(chatService.getChatThreadsByUser(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-chat-previews-by-user")
    public ResponseEntity<?> getChatPreviewByUser(@RequestParam String userId){
        try {
            return ResponseEntity.ok(chatService.getChatPreviewByUser(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
