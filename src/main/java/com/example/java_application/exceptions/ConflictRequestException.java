package com.example.java_application.exceptions;

public class ConflictRequestException extends RuntimeException{
    public ConflictRequestException(String msg){
        super(msg);
    }
}
