package com.example.java_application.entities.BookDto.v1;
import java.util.Objects;

public class BookDtoV1 {
    
    private Long id;
    private String bookName;


    public BookDtoV1() {
    }

    public BookDtoV1(Long id, String bookName) {
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

    public BookDtoV1 id(Long id) {
        setId(id);
        return this;
    }

    public BookDtoV1 bookName(String bookName) {
        setBookName(bookName);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BookDtoV1)) {
            return false;
        }
        BookDtoV1 bookDto = (BookDtoV1) o;
        return Objects.equals(id, bookDto.id) && Objects.equals(bookName, bookDto.bookName);
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
