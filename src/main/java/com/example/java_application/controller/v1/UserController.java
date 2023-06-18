package com.example.java_application.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_application.entities.userDto.UserDtoV1;
import com.example.java_application.services.UserService;
import com.example.java_application.util.Mapper;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOneUser(@PathVariable(value = "user_id") Long id){
        return ResponseEntity.ok().body(Mapper.parseObject(userService.getUser(id), UserDtoV1.class)); 
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok().body(Mapper.parseObjectList(userService.getAllUsers(), UserDtoV1.class)); 
    }

    // @PostMapping
    // public ResponseEntity<?> postUser(){
        
    // }
}
