package com.mon.bookstore.controller;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookAddRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
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
            description = "Add a book to the store")
    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookAddRequestDto dto){
        return new ResponseEntity<>(bookService.addBook(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Fetch a book by isbn",
            description = "fetches a book entity by isbn from the book store")
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<List<BookDto>> fetchBookByIsbn(@PathVariable String isbn){
        return new ResponseEntity<>(bookService.fetchBookByIsbnLike(isbn), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch a book by title",
            description = "fetches a book entity by title from the book store")
    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDto>> fetchBookByTitle(@PathVariable String title){
        return ResponseEntity.ok(bookService.fetchBookByTitleLike(title));
    }

    @Operation(
            summary = "Fetch all books",
            description = "Fetches all book entities and their data from data source")
    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> fetchAllBooks(){
        return ResponseEntity.ok(bookService.fetchAllBooks());
    }

    @Operation(
            summary = "Fetch all available books",
            description = "Fetches all available book entities and their data from data source")
    @GetMapping("/all-available")
    public ResponseEntity<List<BookDto>> fetchAllAvailableBooks(){
        return ResponseEntity.ok(bookService.fetchAllAvailableBooks());
    }

    @Operation(
            summary = "Update book details",
            description = "Updates the details of a book")
    @PatchMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookUpdateRequestDto dto, @PathVariable String isbn){
        return ResponseEntity.accepted().body(bookService.updateBookDetails(dto, isbn));
    }

    @Operation(
            summary = "Delete book",
            description = "Delete a book from the store")
    @DeleteMapping("/isbn/{isbn}")
    public void deleteBook(@PathVariable String isbn){
        bookService.deleteBook(isbn);
    }
}
