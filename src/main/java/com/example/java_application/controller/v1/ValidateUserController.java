package com.example.java_application.controller.v1;

import org.springframework.web.bind.annotation.RestController;

import com.example.java_application.auth.Token;
import com.example.java_application.entities.UserEntity;
import com.example.java_application.entities.userDto.UserDtoV1;
import com.example.java_application.exceptions.BadRequestException;
import com.example.java_application.exceptions.ResponseException;
import com.example.java_application.services.EmailService;
import com.example.java_application.services.UserService;
import com.example.java_application.util.Mapper;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/v1/users/validate")
@Tag(name = "validate_users", description = "Validate users endpoints")
public class ValidateUserController {
    
    @Value("${BASE_URL}")
    private String baseUrl;

    @Autowired
    private EmailService emailService;
    
    @Autowired 
    private UserService userService;

    @Operation(responses = {
        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Token.class))),
        @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(schema = @Schema(implementation = ResponseException.class))),
        @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

    })
    @GetMapping(value = "/{base64String}")
    public ResponseEntity<?> validateUser(@PathVariable(value = "base64String") String encodedString, @RequestHeader(value = "Authorization") String token){
        String decodedId = new String(Base64.getDecoder().decode(encodedString), StandardCharsets.UTF_8).split(":")[0];
        String id = new Token(token.split(" ")[1]).parseTokenToObject().getId();
        
        if(id.equals(decodedId)){
            UserEntity entity = userService.getUser(Long.parseLong(decodedId));
            
            entity.getValidateUser().setIsValid(true);
            entity.getValidateUser().setValidatedDate(new Date());
            
            userService.saveUser(entity);
            
            Token output = new Token();
            output.GenerateToken(Mapper.parseObject(entity, UserDtoV1.class));
            
            return ResponseEntity.ok().body(output);
        }
        

        throw new BadRequestException("Invalid route");
    }
    @PostMapping(value = "/send/email")
    @Operation(description = "Send an email with a link to validate user, use auth token in the link",responses = {
        
        @ApiResponse(description = "Success", responseCode = "200"),
        @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

    })
    public ResponseEntity<?> sendEmail(@RequestHeader(value = "Authorization") String token) {
        
        Claims claims =  new Token(token.split(" ")[1]).parseTokenToObject();

        String email = (String) claims.get("email");
        String id = claims.getId();

        String genSalt = Base64.getEncoder().encodeToString((id+":"+email).getBytes());
        emailService.sendSimpleMessage(email,"Validate email", baseUrl +"/api/v1/users/validate/" + genSalt );
        
        return ResponseEntity.ok().build();
    }
    
    
}
