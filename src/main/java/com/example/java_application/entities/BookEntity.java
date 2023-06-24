package com.example.java_application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "book_name", nullable = false)
    private String bookName;
    
    public BookEntity() {
    }

    public BookEntity(Long id, String bookName) {
        this.id = id;
        this.bookName = bookName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public BookEntity id(Long id) {
        setId(id);
        return this;
    }

    public BookEntity bookName(String bookName) {
        setBookName(bookName);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BookEntity)) {
            return false;
        }
        BookEntity bookEntity = (BookEntity) o;
        return Objects.equals(id, bookEntity.id) && Objects.equals(bookName, bookEntity.bookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookName);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", bookName='" + getBookName() + "'" +
            "}";
    }
    
}
