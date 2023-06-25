package com.example.java_application.util;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8"); 
        Map<String, Object> responseBody = new HashMap<>();

        responseBody.put("timestamp", new Date());
        responseBody.put("msg", authException.getMessage());
        responseBody.put("details", request.getRequestURI());
    
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
            }

}
