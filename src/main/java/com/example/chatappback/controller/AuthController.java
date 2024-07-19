package com.example.chatappback.controller;

import com.example.chatappback.entity.JwtSuccessResponse;
import com.example.chatappback.entity.User;
import com.example.chatappback.entity.UserStatusEnum;
import com.example.chatappback.repository.UserRepository;
import com.example.chatappback.service.JwtService;
import com.example.chatappback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody User user) {
        User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), UserStatusEnum.Offline);
        userService.saveUser(newUser);
        return ResponseEntity.ok("User was created success");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        System.out.println(user.getUsername());
        Optional<User> userOptional = userService.findUserByUsername(user.getUsername());
        if (!userOptional.isPresent()) {
            System.out.println("User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password or username");
        }

        User existingUser = userOptional.get();
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            System.out.println("Incorrect password");
            System.out.println("DB: " + existingUser.getPassword());
            System.out.println("MY: " + user.getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }   else {
            try {
                System.out.println("GOGOGOG");
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String jwtToken = jwtService.generateToken((User) userDetails);
                System.out.println("FROM SERVER: " + jwtToken);
                return ResponseEntity.ok(new JwtSuccessResponse(jwtToken));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            }
        }


    }
}


