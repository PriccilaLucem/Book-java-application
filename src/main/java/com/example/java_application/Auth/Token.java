package com.example.java_application.Auth;
import java.util.Objects;

public class Token {

    private String token;


    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Token token(String token) {
        setToken(token);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Token)) {
            return false;
        }
        Token token = (Token) o;
        return Objects.equals(token, token.token);
    }


    @Override
    public String toString() {
        return "{" +
            " token='" + getToken() + "'" +
            "}";
    }
    
}

