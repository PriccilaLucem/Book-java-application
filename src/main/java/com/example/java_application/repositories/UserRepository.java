package com.example.java_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_application.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
    
}
