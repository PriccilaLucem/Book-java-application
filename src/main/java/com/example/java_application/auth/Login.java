package com.example.java_application.auth;



import org.mindrot.jbcrypt.BCrypt;

import com.example.java_application.exceptions.BadRequestException;

public class Login {
    private String email;
    private String password;

    public Login() {
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void checkPassword(String password) {
        if(!BCrypt.checkpw(this.password, password)){
            throw new BadRequestException("Password does not match");

        };
    }

    
}
