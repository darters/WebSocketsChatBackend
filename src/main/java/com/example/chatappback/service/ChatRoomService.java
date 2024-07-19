package com.example.chatappback.service;

import com.example.chatappback.entity.ChatRoom;
import com.example.chatappback.entity.User;
import com.example.chatappback.repository.ChatRoomRepository;
import com.example.chatappback.repository.MessageRepository;
import com.example.chatappback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public ChatRoom createChatRoom(String user1Id, String user2Id) throws IllegalAccessException {
        ChatRoom chatRoom = chatRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if(chatRoom != null) {
            return chatRoom;
        }

        User user1 = userRepository.findUserById(user1Id);
        User user2 = userRepository.findUserById(user2Id);
        if (user1 == null || user2 == null) {
            throw new IllegalAccessException("User not found");
        }

        chatRoom = ChatRoom.builder()
                .user1Id(user1Id)
                .user2Id(user2Id)
                .user1Name(user1.getUsername())
                .user2Name(user2.getUsername())
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> getUserChatRooms(String userId) {
        return chatRoomRepository.findChatRoomsByUser1IdOrUser2Id(userId, userId);
    }
    public void deleteChatRoomById(String chatId) {
        messageRepository.deleteMessagesByChatRoomId(chatId);
        chatRoomRepository.deleteChatRoomById(chatId);
    }
}
