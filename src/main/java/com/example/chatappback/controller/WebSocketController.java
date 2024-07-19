package com.example.chatappback.controller;

import com.example.chatappback.entity.Message;
import com.example.chatappback.repository.MessageRepository;
import com.example.chatappback.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;


@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        messageRepository.save(message);
        messagingTemplate.convertAndSendToUser(message.getChatRoomId(), "/queue/messages", message);
    }

    @MessageMapping("/status")
    public void processUserStatus(@Payload Map<String, String> statusMessage) {
        String userId = statusMessage.get("userId");
        String userStatus = statusMessage.get("status");
        System.out.println("WEB SOCKET USER ID: " + userId + " STATUS: " + userStatus);

        messagingTemplate.convertAndSend("/topic/userStatus/" + userId , statusMessage);
    }
}
