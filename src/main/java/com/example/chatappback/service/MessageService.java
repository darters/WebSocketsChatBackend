package com.example.chatappback.service;

import com.example.chatappback.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MongoTemplate mongoTemplate;
    public void updateMessagesStatusToRead(String chatRoomId, String currentUserId) {
        Query query = new Query(Criteria.where("chatRoomId").is(chatRoomId).and("messageStatus").is("DELIVERED").and("senderId").ne(currentUserId));
        Update update = new Update().set("messageStatus", "READ");
        mongoTemplate.updateMulti(query, update, Message.class);
    }
}
