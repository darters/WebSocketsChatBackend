package com.example.chatappback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String chatRoomId;
    private String senderId;
    private String content;
    private String timestamp;
    private MessageStatus messageStatus;

}
