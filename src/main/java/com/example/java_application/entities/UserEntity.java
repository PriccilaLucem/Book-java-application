package com.example.java_application.entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

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
    private ValidateUserEntity validateUser;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_book_list", joinColumns = @JoinColumn(name="book_id"),
    inverseJoinColumns = @JoinColumn(name="user_id")
    )
    Set<BookEntity> bookList = new HashSet<>();

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

    public void addBook(BookEntity book){
        this.bookList.add(book);
    }

    public void removeBook(BookEntity book){
        this.bookList.remove(book);
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public Set<BookEntity> getBookList() {
        return this.bookList;
    }

    public void setBookList(Set<BookEntity> bookList) {
        this.bookList = bookList;
    }
    
    public ValidateUserEntity getValidateUser() {
        return this.validateUser;
    }

    public void setValidateUser(ValidateUserEntity validateUser) {
        this.validateUser = new ValidateUserEntity();
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

   
}
