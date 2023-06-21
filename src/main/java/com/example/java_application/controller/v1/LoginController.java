package com.example.java_application.controller.v1;

import org.springframework.web.bind.annotation.RestController;

import com.example.java_application.auth.Login;
import com.example.java_application.auth.Token;
import com.example.java_application.entities.UserEntity;
import com.example.java_application.entities.userDto.UserDtoV1;
import com.example.java_application.services.UserService;
import com.example.java_application.util.Mapper;

import org.springframework.web.bind.annotation.RequestMapping;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private UserService userService;
    
    @PostMapping()
    public ResponseEntity<?> postMethodName(@RequestBody Login entity) {
        try{
        UserEntity user = userService.findUserByEmail(entity.getEmail());
        entity.checkPassword(user.getPassword());
           Token token = new Token();
           token.GenerateToken(Mapper.parseObject(user, UserDtoV1.class));
           return ResponseEntity.ok().body(token);
    
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("No such alghortim");
        }
        
    }

    
}
