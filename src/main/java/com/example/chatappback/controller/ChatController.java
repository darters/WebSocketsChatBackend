package com.example.chatappback.controller;

import com.example.chatappback.entity.ChatRoom;
import com.example.chatappback.entity.Message;
import com.example.chatappback.entity.User;
import com.example.chatappback.entity.UserStatusEnum;
import com.example.chatappback.repository.MessageRepository;
import com.example.chatappback.request.CreateRoomRequest;
import com.example.chatappback.service.ChatRoomService;
import com.example.chatappback.service.MessageService;
import com.example.chatappback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatRoom")
public class ChatController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody CreateRoomRequest request) throws IllegalAccessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user1 = (User) authentication.getPrincipal();
        System.out.println("USER1: " + user1.getId());
        System.out.println("USER2: " + request.getUser2Id());
        ChatRoom chatRoom = chatRoomService.createChatRoom(user1.getId(), request.getUser2Id());
        System.out.println("Successful");
        return ResponseEntity.ok(chatRoom);
    }
    @DeleteMapping("/delete/{chatRoomId}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable String chatRoomId) {
        chatRoomService.deleteChatRoomById(chatRoomId);
        return ResponseEntity.ok("ChatRoom was successfully deleted");
    }
    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId) {
        messageRepository.deleteMessageById(messageId);
        return ResponseEntity.ok("Message was successfully deleted");
    }
    @GetMapping("/get")
    public List<ChatRoom> getChatRooms() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<ChatRoom> chatRooms = chatRoomService.getUserChatRooms(user.getId());
        return chatRooms;
    }
    @GetMapping("/messages/{chatRoomId}")
    public List<Message> getMessages(@PathVariable String chatRoomId, @RequestParam String openerId) {
        List<Message> messages = messageRepository.findMessagesByChatRoomId(chatRoomId);
        if (!messages.isEmpty()) {
            Message lastMessage = messages.get(messages.size() - 1);
            if (!lastMessage.getSenderId().equals(openerId)) {
                messageService.updateMessagesStatusToRead(chatRoomId, openerId);
                messages = messageRepository.findMessagesByChatRoomId(chatRoomId);
                return messages;
            }
        }
        System.out.println(messages);
        return messages;
    }
}


