package com.example.chatappback.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(accessor != null) {
            StompCommand stompCommand = accessor.getCommand();
            if(StompCommand.CONNECT.equals(stompCommand)) {
                String token = accessor.getFirstNativeHeader("Authorization");
            }
        } else {
            System.out.println("ACCESSOR IS NULL");
        }
    return message;
    }
}
