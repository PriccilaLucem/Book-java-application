package com.example.java_application.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ForbiddendException extends AccessDeniedHandlerImpl{
    private final ObjectMapper objectMapper;


    public ForbiddendException(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8"); // Set response content type to application/json
        Map<String, Object> responseBody = new HashMap<>();

        responseBody.put("timestamp", new Date());
        responseBody.put("msg", accessDeniedException.getMessage());
        responseBody.put("description", "Invalid or missing token");

        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}