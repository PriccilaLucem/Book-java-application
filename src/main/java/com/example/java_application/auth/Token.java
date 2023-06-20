package com.example.java_application.auth;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.List;



import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;



import com.example.java_application.entities.userDto.UserDtoV1;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
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

    public static String GenerateToken(UserDtoV1 user) throws NoSuchAlgorithmException {
        final String SECRET_KEY = Dotenv.load().get("SECRET_KEY");
        byte[] secretKeyBytes  = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        SecretKey key = new SecretKeySpec(secretKeyBytes,SignatureAlgorithm.HS256.getJcaName());
        String token = Jwts.builder()

        .signWith(key,SignatureAlgorithm.HS256)
        .setId(Long.toString(user.getId()))
        .claim("email", user.getEmail())
        .claim("is valid", user.getValidateUser().getIsValid())
        .claim("nick_name", user.getNickName())
        .compact();

        return token;

    }
 
      public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.split(" ")[1];
        }
        return null;
    }

    public boolean validateToken(String jwtToken) {
        final String SECRET_KEY = Dotenv.load().get("SECRET_KEY");
        
        byte[] secretKeyBytes  = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        SecretKey key = new SecretKeySpec(secretKeyBytes,SignatureAlgorithm.HS256.getJcaName());
        try{
            Jwts.parserBuilder()
            .setSigningKey(key).build().parse(jwtToken);
            return true;

        }catch(JwtException e){
            return false;
        }
        
    }

     public Authentication getAuthentication(String token) {
        String userId = parseUserIdFromToken(token);

        List<String> roles = parseRolesFromToken(token);

        System.out.println(roles);
        List<SimpleGrantedAuthority> authorities = createAuthorities(roles);

        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }


    private String parseUserIdFromToken(String token) { 
        final String SECRET_KEY = Dotenv.load().get("SECRET_KEY");
        byte[] secretKeyBytes = Base64.getEncoder().encode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKeyBytes))
                .build()
                .parseClaimsJws(token)
                .getBody();

                
        return claims.getId();

    }

    private List<String> parseRolesFromToken(String token) {
        final String SECRET_KEY = Dotenv.load().get("SECRET_KEY");

        byte[] secretKeyBytes = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKeyBytes))
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = new ArrayList<>();

        String email = claims.getSubject();
        if (email != null && email.endsWith("@admin.com")) {
            roles.add("ROLE_ADMIN");
        } else {
            roles.add("ROLE_ADMIN");
        }
        
        return roles;
    }

    private List<SimpleGrantedAuthority> createAuthorities(List<String> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}


