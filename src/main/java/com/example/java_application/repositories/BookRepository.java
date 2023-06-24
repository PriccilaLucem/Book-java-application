package com.example.java_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_application.entities.BookEntity;



public interface BookRepository extends JpaRepository<BookEntity,Long> {
    
    public BookEntity findByBookName(String bookName);

    default BookEntity getOrCreate(String bookName){
        BookEntity book = findByBookName(bookName);
        if(book == null){
            book = new BookEntity(null, bookName);
            save(book);
            return book;
        }else{
            return book;
        }

    }
}
