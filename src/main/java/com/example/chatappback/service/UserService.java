package com.example.chatappback.service;

import com.example.chatappback.entity.User;
import com.example.chatappback.entity.UserStatusEnum;
import com.example.chatappback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository userRepository;
    public void updateUserStatus(String userId, UserStatusEnum userStatusEnum) {
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update().set("userStatus", userStatusEnum);
        mongoTemplate.updateMulti(query, update, User.class);
        System.out.println(userId + "FFJDSKFJKDSFJKJJJJJJJJJS");
    }
    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}