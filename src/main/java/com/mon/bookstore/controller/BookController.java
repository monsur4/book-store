package com.mon.bookstore.controller;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookCreateRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
import com.mon.bookstore.dto.response.RetrievedBooksResponseDto;
import com.mon.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(
            summary = "Add books",
            description = "Add a book to the data source")
    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookCreateRequestDto dto){
        return new ResponseEntity<>(bookService.addBook(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Fetch a book by id",
            description = "fetches a book entity by id and its data from data source")
    @GetMapping("/id/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable String id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(
            summary = "Fetch all books",
            description = "Fetches all book entities and their data from data source")
    @GetMapping()
    public ResponseEntity<List<BookDto>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(
            summary = "Retrieve books",
            description = "Retrieve a specified number of books from data source")
    @PatchMapping("/retrieve/title/{title}/number/{number}")
    public ResponseEntity<RetrievedBooksResponseDto> retrieveBooks(@PathVariable("title") String title,
                                                                   @PathVariable("number") Integer number){
        return ResponseEntity.accepted().body(bookService.retrieveBooks(title, number));
    }

    @Operation(
            summary = "Update book details",
            description = "Updates the details of a book")
    @PatchMapping("/id/{id}")
    public ResponseEntity<?> updateBook(@RequestBody BookUpdateRequestDto dto, @PathVariable String id){
        return ResponseEntity.accepted().body(bookService.updateBookDetails(dto, id));
    }

    @Operation(
            summary = "Delete book",
            description = "Delete a book by id from data source")
    @DeleteMapping("/id/{id}")
    public void deleteBook(@PathVariable String id){
        bookService.deleteBookById(id);
    }
}
