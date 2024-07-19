package com.example.chatappback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusRequest {
    private String userId;
    private UserStatusEnum status;
}
