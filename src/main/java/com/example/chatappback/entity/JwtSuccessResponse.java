package com.example.chatappback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtSuccessResponse {
    private String token;
}
