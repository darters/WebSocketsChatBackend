package com.example.chatappback.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private UserStatusEnum userStatus;
    public User(String username, String password, UserStatusEnum userStatusEnum) {
        this.username = username;
        this.password = password;
        this.userStatus = userStatusEnum;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

}

