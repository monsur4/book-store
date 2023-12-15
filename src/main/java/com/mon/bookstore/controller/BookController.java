package com.mon.bookstore.controller;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookAddRequestDto;
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
    @GetMapping()
    public ResponseEntity<List<BookDto>> fetchAllBooks(){
        return ResponseEntity.ok(bookService.fetchAllBooks());
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
    @PatchMapping("/title/{title}")
    public ResponseEntity<?> updateBook(@RequestBody BookUpdateRequestDto dto, @PathVariable String title){
        return ResponseEntity.accepted().body(bookService.updateBookDetails(dto, title));
    }

    @Operation(
            summary = "Delete book",
            description = "Delete a book by title from data source")
    @DeleteMapping("/title/{title}")
    public void deleteBook(@PathVariable String title){
        bookService.deleteBookByTitle(title);
    }
}
