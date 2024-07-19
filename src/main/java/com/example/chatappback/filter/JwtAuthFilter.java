package com.example.chatappback.filter;

import com.example.chatappback.service.JwtService;
import com.example.chatappback.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HEADER_NAME);
        if (request.getServletPath().contains("/auth") || !StringUtils.hasText(header) || !header.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("+++++++++++++++++++++++++++++ \n " + header);
        if(header.startsWith(BEARER_PREFIX) && StringUtils.hasText(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());
            try {
                String username = jwtService.getUsername(token);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
//                    if(!jwtService.isTokenExpired(token)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}
