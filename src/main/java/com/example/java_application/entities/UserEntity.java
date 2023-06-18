package com.example.java_application.entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import org.mindrot.jbcrypt.BCrypt;

import com.example.java_application.exceptions.BadRequestException;

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
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "validate_user_id", nullable = true)
    private validateUser validateUser;


    public UserEntity() {
    }


    public UserEntity(Long id, String email, String password, String nickName) {
        this.id = id;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.nickName = nickName;
    }

    @PrePersist
    public void ValidateData(){
    
        if(!isValidEmail(this.email)){
            System.out.println(this.email);
            throw new BadRequestException("Invalid email addresss");
        }
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
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
        this.validateUser = new validateUser();
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

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String name) {
        this.nickName = name;
    }

    public Boolean checkPassword(String password){
        return BCrypt.checkpw(password, this.password);
    }
}
