package com.example.java_application.controller.v1;

import org.springframework.web.bind.annotation.RestController;

import com.example.java_application.auth.Token;
import com.example.java_application.services.EmailService;

import io.jsonwebtoken.Claims;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/v1/users/validate")
public class ValidateUserController {
    
    @Value("${BASE_URL}")
    private String baseUrl;

    @Autowired
    private EmailService emailService;
    
    @PostMapping(value = "/send/email")
    public ResponseEntity<?> postMethodName(@RequestHeader(value = "Authorization") String token) {
        
        Claims claims =  new Token(token.split(" ")[1]).parseTokenToObject();

        String email = (String) claims.get("email");
        String id = claims.getId();

        String genSalt = Base64.getEncoder().encodeToString((id+":"+email).getBytes());
        emailService.sendSimpleMessage(email,"Validate email", baseUrl +"/api/v1/users/validate/" + genSalt );
        
        return ResponseEntity.ok().build();
    }
    
    
}
