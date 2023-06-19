package com.example.java_application.Auth;

import java.security.NoSuchAlgorithmException;


import org.mindrot.jbcrypt.BCrypt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.java_application.entities.userDto.UserDtoV1;
import com.example.java_application.exceptions.UnauthorizedException;

import io.github.cdimascio.dotenv.Dotenv;

public class Login {
    private String email;
    private String password;
    private final Dotenv DOT_ENV = Dotenv.configure().load();

    private final String SECRET_KEY = DOT_ENV.get("SECRET_KEY");

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
            throw new UnauthorizedException("Password does not match");

        };
    }

    public String GenerateToken(UserDtoV1 user) throws NoSuchAlgorithmException {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("id", user.getId())
                .withClaim("nick_name", user.getNickName())
                .withClaim("email", user.getEmail())
                .withClaim("is_valid", user.getValidateUser().getIsValid())
                .sign(algorithm);

        return token;

    }
}
