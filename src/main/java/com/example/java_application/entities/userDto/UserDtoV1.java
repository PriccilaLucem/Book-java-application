package com.example.java_application.entities.userDto;

import com.example.java_application.entities.validateUser;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UserDtoV1 {
    private Long id;
    private String nickName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private validateUser validateUser;

    public UserDtoV1() {
    }

    public UserDtoV1(String email, Long id, String password) {
        this.email = email;
        this.id = id;
        this.password = password;
    }


    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public validateUser getValidateUser() {
        return this.validateUser;
    }

    public void setValidateUser(validateUser validateUser) {
        this.validateUser = validateUser;
    }

    public UserDtoV1 email(String email) {
        setEmail(email);
        return this;
    }

    public UserDtoV1 id(Long id) {
        setId(id);
        return this;
    }

    public UserDtoV1 password(String password) {
        setPassword(password);
        return this;
    }

    public UserDtoV1 validateUser(validateUser validateUser) {
        setValidateUser(validateUser);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserDtoV1)) {
            return false;
        }
        UserDtoV1 userDtoV1 = (UserDtoV1) o;
        return Objects.equals(email, userDtoV1.email) && Objects.equals(id, userDtoV1.id) && Objects.equals(password, userDtoV1.password) && Objects.equals(validateUser, userDtoV1.validateUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id, password, validateUser);
    }

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", id='" + getId() + "'" +
            ", password='" + getPassword() + "'" +
            ", validateUser='" + getValidateUser() + "'" +
            "}";
    }
    

}
