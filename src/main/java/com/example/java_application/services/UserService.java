package com.example.java_application.services;

import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

import com.example.java_application.entities.UserEntity;
import com.example.java_application.exceptions.NotFoundException;
import com.example.java_application.repositories.UserRepository;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;


    public UserEntity getUser(long id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return user;
    }

    public List<UserEntity> getAllUsers(){
        List<UserEntity> users = userRepository.findAll();
        return users;
    }
}
