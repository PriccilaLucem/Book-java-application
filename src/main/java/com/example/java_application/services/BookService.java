package com.example.java_application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java_application.entities.BookEntity;
import com.example.java_application.entities.BookDto.v1.BookDtoV1;
import com.example.java_application.exceptions.NotFoundException;
import com.example.java_application.repositories.BookRepository;
import com.example.java_application.util.Mapper;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;


    public List<BookDtoV1> getAllBooks(){
        return Mapper.parseObjectList(bookRepository.findAll(), BookDtoV1.class);
    }

    public BookDtoV1 getOneBook(Long bookId){
        return Mapper.parseObject(bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found") ), BookDtoV1.class);
    }

    public BookDtoV1 createBook(BookEntity book){
        return Mapper.parseObject(bookRepository.getOrCreate(book.getBookName()), BookDtoV1.class);
    }
    public void deleteBook(Long bookId){
        bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
        bookRepository.deleteById(bookId);
    }


}
