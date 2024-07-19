package com.example.chatappback.repository;

import com.example.chatappback.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    ChatRoom findByUser1IdAndUser2Id(String user1Id, String user2Id);
    List<ChatRoom> findChatRoomsByUser1IdOrUser2Id(String user1Id, String user2Id);
    void deleteChatRoomById(String chatRoomId);
}
