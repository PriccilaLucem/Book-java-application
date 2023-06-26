package com.example.java_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;

import com.example.java_application.auth.JwtTokenFilter;
import com.example.java_application.util.CustomAuthEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
public class JwtSecurityConfig{
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                .addFilterAfter(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic((HttpBasicConfigurer) -> HttpBasicConfigurer.disable())
                .csrf((csrfConfigurer) -> csrfConfigurer.disable())
                .sessionManagement((SessionManagement) -> SessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.POST, "/api/v1/login", "api/v1/users", "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/validate/**").hasRole("USER_IS_NOT_VALID")
                .requestMatchers("/api/v1/users").hasRole("USER_IS_VALID")
                .requestMatchers("/api/v1/books/**").hasRole("USER_IS_VALID")
                .requestMatchers("/**").permitAll()
                );

        http.
                rememberMe((remember) -> remember
                     .rememberMeServices(rememberMeServices)
                    )
                .exceptionHandling((configurer) -> 
                configurer.authenticationEntryPoint(new CustomAuthEntryPoint())
                );

                 
        return http.build(); 
    }
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        Dotenv dotenv = Dotenv.load();
        
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(dotenv.get("SECRET_KEY"), userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.SHA256);
        return rememberMe;
    }

     @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler() {
        return new DefaultWebSecurityExpressionHandler();
    }
  

}