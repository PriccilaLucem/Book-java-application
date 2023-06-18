package com.example.java_application.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100, name = "email")
    private String email;
    @Column(name = "password", nullable = false)
    private String password; 
    @Column(name = "nick_name", nullable = false)
    private String nickName;
    @OneToOne
    @JoinColumn(name = "validate_user_id", nullable = false)
    private validateUser validateUser;


    public UserEntity() {
    }


    public UserEntity(Long id, String email, String password, String nickName, validateUser validateUser) {
        this.id = id;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.nickName = nickName;
        this.validateUser = validateUser;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }


    public validateUser getValidateUser() {
        return this.validateUser;
    }

    public void setValidateUser(validateUser validateUser) {
        this.validateUser = validateUser;
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

    public String getName() {
        return this.nickName;
    }

    public void setName(String name) {
        this.nickName = name;
    }

    public Boolean checkPassword(String password){
        return BCrypt.checkpw(password, this.password);
    }
}
