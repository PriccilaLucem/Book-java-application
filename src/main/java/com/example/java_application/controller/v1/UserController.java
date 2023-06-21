package com.example.java_application.controller.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_application.entities.UserEntity;
import com.example.java_application.entities.userDto.UserDtoV1;
import com.example.java_application.exceptions.BadRequestException;
import com.example.java_application.exceptions.ConflictRequestException;
import com.example.java_application.services.UserService;
import com.example.java_application.util.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody UserDtoV1 userDtoV1){
        System.out.println(userDtoV1);
        UserEntity user = Mapper.parseObject(userDtoV1, UserEntity.class);
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.parseObject(userService.saveUser(user), UserDtoV1.class));
        }catch(DataIntegrityViolationException e){

            String msg = e.getMessage();
            if(msg.contains("not-null")){
                String error = e.getMessage().split(":")[1].split("\\.")[e.getMessage().split(":")[1].split("\\.").length -1];
                throw new BadRequestException("Value cannot be null: " + error);
            }else{
                throw new ConflictRequestException("Email Already exists");  
            }            
        }
        }   

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "user_id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{user_id}")
    public ResponseEntity<?> putUser(@PathVariable(value = "user_id") Long id, @RequestBody UserDtoV1 userDtoV1){
        userDtoV1.setId(id);
        UserEntity user = userService.putUser(Mapper.parseObject(userDtoV1, UserEntity.class));
        return ResponseEntity.accepted().body(Mapper.parseObject(user,UserDtoV1.class));
    }

    }
