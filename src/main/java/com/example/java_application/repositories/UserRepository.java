package com.example.java_application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_application.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
    
    Optional<UserEntity> findByEmail(String email);
}
