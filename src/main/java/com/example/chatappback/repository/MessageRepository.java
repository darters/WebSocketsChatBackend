package com.example.chatappback.repository;

import com.example.chatappback.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findMessagesByChatRoomId(String chatRoomId);
    void deleteMessageById(String messageId);
    void deleteMessagesByChatRoomId(String chatRoomId);
}
