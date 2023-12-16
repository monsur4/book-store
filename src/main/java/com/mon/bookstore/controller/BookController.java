package com.mon.bookstore.controller;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookAddRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface BookController {

    @Operation(
            summary = "Add books",
            description = "Add a book to the store")
    @PostMapping
    ResponseEntity<BookDto> addBook(@RequestBody @Valid BookAddRequestDto dto);

    @Operation(
            summary = "Delete book",
            description = "Delete a book from the store")
    @DeleteMapping(value = "/isbn/{isbn}")
    ResponseEntity<Void> deleteBook(@PathVariable String isbn);

    @Operation(
            summary = "Fetch all available books",
            description = "Fetches all available book entities and their data from data source")
    @GetMapping("/all-available")
    ResponseEntity<List<BookDto>> fetchAllAvailableBooks();

    @Operation(
            summary = "Fetch all books",
            description = "Fetches all book entities and their data from data source")
    @GetMapping("/all")
    ResponseEntity<List<BookDto>> fetchAllBooks();

    @Operation(
            summary = "Fetch books by isbn",
            description = "Fetch all book entities with matching isbn from the book store")
    @GetMapping("/isbn/{isbn}")
    ResponseEntity<List<BookDto>> fetchBookByIsbn(@PathVariable String isbn);

    @Operation(
            summary = "Fetch a book by title",
            description = "fetches a book entity by title from the book store")
    @GetMapping("/title/{title}")
    ResponseEntity<List<BookDto>> fetchBookByTitle(@PathVariable String title);

    @Operation(
            summary = "Update book details",
            description = "Updates the details of a book")
    @PatchMapping("/isbn/{isbn}")
    ResponseEntity<BookDto> updateBook(@RequestBody BookUpdateRequestDto dto, @PathVariable String isbn);
}
