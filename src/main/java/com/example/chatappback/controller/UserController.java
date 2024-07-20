package com.example.chatappback.controller;

import com.example.chatappback.entity.User;
import com.example.chatappback.entity.UserStatusEnum;
import com.example.chatappback.entity.UserStatusRequest;
import com.example.chatappback.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userController")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<User> getAllUser() {
        return userService.findAllUsers();
    }
    @PostMapping("/changeStatus")
    public ResponseEntity<String> changeUserStatus(@RequestBody UserStatusRequest userStatusRequest) {
        userService.updateUserStatus(userStatusRequest.getUserId(), userStatusRequest.getStatus());
        return ResponseEntity.ok("User set status " + userStatusRequest.getStatus());
    }
    @GetMapping("/getStatus/{userId}")
    public UserStatusEnum getUserStatus(@PathVariable String userId) {
        return userService.getUserStatusById(userId);
    }
}
