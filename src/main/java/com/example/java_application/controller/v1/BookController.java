package com.example.java_application.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_application.entities.BookEntity;
import com.example.java_application.entities.BookDto.v1.BookDtoV1;
import com.example.java_application.services.BookService;
import com.example.java_application.util.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/books")
class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping()
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }
    
    @GetMapping(value = "/{book_id}")
    public ResponseEntity<?>  getOneBook(@PathVariable(value = "book_id") Long bookId){
        return ResponseEntity.ok().body(Mapper.parseObject(bookService.getOneBook(bookId), BookDtoV1.class));
    }
    
    @PostMapping
    public ResponseEntity<?> postBook(@RequestBody BookDtoV1 book){

        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.parseObject(bookService.createBook(Mapper.parseObject(book, BookEntity.class)), BookDtoV1.class));
    }

    @DeleteMapping(value = "/{book_id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value="book_id") Long bookID){
        bookService.deleteBook(bookID);
        return ResponseEntity.status(204).build();
    }

    
}