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
import com.example.java_application.exceptions.ResponseException;
import com.example.java_application.services.UserService;
import com.example.java_application.util.Mapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "users", description = "Users endpoints")
public class UserController {
    
    @Autowired
    private UserService userService;


    @Operation(
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDtoV1.class))),
            @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOneUser(@PathVariable(value = "user_id") Long id){
        return ResponseEntity.ok().body(Mapper.parseObject(userService.getUser(id), UserDtoV1.class)); 
    }

    @Operation(
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDtoV1.class)))),
            @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok().body(Mapper.parseObjectList(userService.getAllUsers(), UserDtoV1.class)); 
    }


    @Operation(
        responses = {
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDtoV1.class))),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

        }
    )
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


    @Operation(
        responses = {
            @ApiResponse(description = "No content", responseCode = "200"),
            @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "user_id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
        responses = {
            @ApiResponse(description = "Success", responseCode = "202", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDtoV1.class))),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    @PutMapping("/{user_id}")
    public ResponseEntity<?> putUser(@PathVariable(value = "user_id") Long id, @RequestBody UserDtoV1 userDtoV1){
        UserEntity entity = userService.getUser(id);
        entity.setNickName(userDtoV1.getNickName());

        userService.putUser(entity);
        return ResponseEntity.accepted().body(Mapper.parseObject(entity, UserDtoV1.class));
    }

    }
