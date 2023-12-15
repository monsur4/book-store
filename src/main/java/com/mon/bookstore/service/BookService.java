package com.mon.bookstore.service;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookAddRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
import com.mon.bookstore.dto.response.RetrievedBooksResponseDto;

import java.util.List;

public interface BookService {

    BookDto addBook (BookAddRequestDto dto);
    List<BookDto> fetchAllBooks();

    BookDto getBookById(String id);

    List<BookDto> getBooksByAuthor(String authorName);

    BookDto getBookIsbn(String isbn);

    BookDto updateBookDetails(BookUpdateRequestDto dto, String id);

    void deleteBookByTitle(String title);

    RetrievedBooksResponseDto retrieveBooks(String title, Integer number);

    List<BookDto> fetchBookByTitleLike(String title);

    List<BookDto> fetchBookByIsbnLike(String isbn);
}
