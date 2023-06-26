package com.example.java_application.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_application.auth.Token;
import com.example.java_application.entities.BookEntity;
import com.example.java_application.entities.UserEntity;
import com.example.java_application.entities.BookDto.v1.BookDtoV1;
import com.example.java_application.exceptions.ResponseException;
import com.example.java_application.services.BookService;
import com.example.java_application.services.UserService;
import com.example.java_application.util.Mapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "books",  description = "Books endpoints")
class BookController {

    @Autowired
    private BookService bookService;
    
    @Autowired 
    private UserService userService;

    
    @Operation(responses = {
        @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookDtoV1.class)))),
        @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllBooks(@RequestHeader String Authorization) {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }
    
    @Operation(responses = {
        @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDtoV1.class))),
        @ApiResponse(description = "Book not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseException.class))),
        @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

    })  
    @GetMapping(value = "/{book_id}", produces = {MediaType.APPLICATION_JSON_VALUE}) 
    public ResponseEntity<?>  getOneBook(@PathVariable(value = "book_id") Long bookId,  @RequestHeader String Authorization){
        return ResponseEntity.ok().body(Mapper.parseObject(bookService.getOneBook(bookId), BookDtoV1.class));
    }
    
    @Operation(responses = {
        @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDtoV1.class))),
        @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseException.class))),
        @ApiResponse(description = "Conflict", responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseException.class))),
        @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postBook(@RequestBody BookDtoV1 book, @RequestHeader String Authorization){
        BookEntity bookEntity = Mapper.parseObject(bookService.createOrGetBook(Mapper.parseObject(book, BookEntity.class)), BookEntity.class);
        BookDtoV1 bookDtoV1= Mapper.parseObject(bookEntity,BookDtoV1.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDtoV1);
    }

    @Operation(responses = {
        @ApiResponse(description = "Deleted", responseCode = "204"),
        @ApiResponse(description = "Book not found", responseCode = "404", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseException.class))),
        @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

    })
    @DeleteMapping(value = "/{book_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBook(@PathVariable(value="book_id") Long bookID,  @RequestHeader String Authorization){
        bookService.deleteBook(bookID);
        return ResponseEntity.status(204).build();
    }
    @Operation(description = "Register the book on the user inventary",
    responses = {
        @ApiResponse(description= "Success", responseCode = "200"),
        @ApiResponse(description= "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseException.class))),
        @ApiResponse(description = "Internal server Error", responseCode = "500", content = @Content(schema = @Schema(implementation = ResponseException.class)))

    })

    @GetMapping(value = "/pick-a-book/{book_id}")
    public ResponseEntity<?> pickABook(@PathVariable(value = "book_id") Long bookId, @RequestHeader String Authorization){
        BookEntity book = Mapper.parseObject(bookService.getOneBook(bookId), BookEntity.class);
        UserEntity user = userService.getUser(Long.parseLong(new Token(Authorization.split(" ")[1]).parseTokenToObject().getId()));

        user.addBook(book);
        userService.saveUser(user);

        return ResponseEntity.ok().build();
    }
    
}