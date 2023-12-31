package com.example.java_application.entities;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "validate_user")
public class ValidateUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "is_valid",columnDefinition = "boolean default false", nullable = false)
    private Boolean isValid;
    @Column(name = "validated_date")
    private Date validatedDate;
  
    public ValidateUserEntity() {
        this.isValid = false;
    }

    public ValidateUserEntity(Long id, Date validatedDate) {
        this.id = id;
        this.validatedDate = validatedDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsValid() {
        return this.isValid;
    }

    public Boolean getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Date getValidatedDate() {
        return this.validatedDate;
    }

    public void setValidatedDate(Date validatedDate) {
        this.validatedDate = validatedDate;
    }

    public ValidateUserEntity id(Long id) {
        setId(id);
        return this;
    }

    public ValidateUserEntity isValid(Boolean isValid) {
        setIsValid(isValid);
        return this;
    }

    public ValidateUserEntity validatedDate(Date validatedDate) {
        setValidatedDate(validatedDate);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ValidateUserEntity)) {
            return false;
        }
        ValidateUserEntity validateUser = (ValidateUserEntity) o;
        return Objects.equals(id, validateUser.id) && Objects.equals(isValid, validateUser.isValid) && Objects.equals(validatedDate, validateUser.validatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isValid, validatedDate);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", isValid='" + isIsValid() + "'" +
            ", validatedDate='" + getValidatedDate() + "'" +
            "}";
    }
    
}
