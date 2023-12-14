package com.mon.bookstore.service;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookCreateRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
import com.mon.bookstore.dto.response.RetrievedBooksResponseDto;

import java.util.List;

public interface BookService {

    BookDto addBook (BookCreateRequestDto dto);
    List<BookDto> getAllBooks();

    BookDto getBookById(String id);

    List<BookDto> getBooksByAuthor(String authorName);

    BookDto getBookIsbn(String isbn);

    BookDto updateBookDetails(BookUpdateRequestDto dto, String id);

    void deleteBookById(String id);

    RetrievedBooksResponseDto retrieveBooks(String title, Integer number);

    BookDto getBookByTitle(String title);
}
