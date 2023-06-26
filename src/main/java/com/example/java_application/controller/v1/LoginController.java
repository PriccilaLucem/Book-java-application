package com.example.java_application.controller.v1;

import org.springframework.web.bind.annotation.RestController;

import com.example.java_application.auth.Login;
import com.example.java_application.auth.Token;
import com.example.java_application.entities.UserEntity;
import com.example.java_application.entities.userDto.UserDtoV1;
import com.example.java_application.exceptions.ResponseException;
import com.example.java_application.services.UserService;
import com.example.java_application.util.Mapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/login")
@Tag(name = "login", description = "User login endpoint")
public class LoginController {

    @Autowired
    private UserService userService;
    
    @Operation(responses = {
        @ApiResponse(description = "Success" ,responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class))),
        @ApiResponse(description = "Email not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseException.class))),
        @ApiResponse(description = "Invalid Password", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseException.class))),
        @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postMethodName(@RequestBody Login entity) {
        UserEntity user = userService.findUserByEmail(entity.getEmail());
        entity.checkPassword(user.getPassword());
        Token token = new Token();
        token.GenerateToken(Mapper.parseObject(user, UserDtoV1.class));
        return ResponseEntity.ok().body(token);
    }
}
