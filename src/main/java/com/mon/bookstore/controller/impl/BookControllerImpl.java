package com.mon.bookstore.controller.impl;

import com.mon.bookstore.controller.BookController;
import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookAddRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
import com.mon.bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookAddRequestDto dto){
        return new ResponseEntity<>(bookService.addBook(dto), HttpStatus.CREATED);
    }

    public ResponseEntity<List<BookDto>> fetchBookByIsbn(@PathVariable String isbn){
        return new ResponseEntity<>(bookService.fetchBookByIsbnLike(isbn), HttpStatus.OK);
    }

    public ResponseEntity<List<BookDto>> fetchBookByTitle(@PathVariable String title){
        return ResponseEntity.ok(bookService.fetchBookByTitleLike(title));
    }

    public ResponseEntity<List<BookDto>> fetchAllBooks(){
        return ResponseEntity.ok(bookService.fetchAllBooks());
    }

    public ResponseEntity<List<BookDto>> fetchAllAvailableBooks(){
        return ResponseEntity.ok(bookService.fetchAllAvailableBooks());
    }

    public ResponseEntity<BookDto> updateBook(@RequestBody BookUpdateRequestDto dto, @PathVariable String isbn){
        return ResponseEntity.accepted().body(bookService.updateBookDetails(dto, isbn));
    }

    public ResponseEntity<Void> deleteBook(@PathVariable String isbn){
        bookService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }
}
