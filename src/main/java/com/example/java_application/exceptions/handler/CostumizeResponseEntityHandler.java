package com.example.java_application.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.java_application.exceptions.NotFoundException;
import com.example.java_application.exceptions.ResponseException;

@RestController
@ControllerAdvice
public class CostumizeResponseEntityHandler extends ResponseEntityExceptionHandler{
    
      
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ResponseException> handleNotFound(Exception ex, WebRequest wr){
        ResponseException exception = new ResponseException(new Date(), ex.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseException> handleAllExceptions(Exception ex, WebRequest wr){
        ResponseException exception = new ResponseException(new Date(), ex.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
