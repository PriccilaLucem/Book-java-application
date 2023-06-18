package com.example.java_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_application.entities.validateUser;

public interface ValidateUserRepository extends JpaRepository<validateUser,Long>{
    
}
