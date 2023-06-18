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

    public UserEntity createNewUser(UserEntity user){
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        this.getUser(id);
        userRepository.deleteById(id);
    }

    public UserEntity putUser(UserEntity entity){
        UserEntity user = this.getUser(entity.getId());
        
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        
        return userRepository.save(entity);
    }
}
