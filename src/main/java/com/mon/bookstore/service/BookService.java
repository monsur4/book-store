package com.mon.bookstore.service;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookAddRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;

import java.util.List;

public interface BookService {

    BookDto addBook (BookAddRequestDto dto);

    void deleteBook(String isbn);

    List<BookDto> fetchAllAvailableBooks();

    List<BookDto> fetchAllBooks();

    List<BookDto> fetchBookByIsbnLike(String isbn);

    List<BookDto> fetchBookByTitleLike(String title);

    List<BookDto> getBooksByAuthor(String authorName);

    BookDto updateBookDetails(BookUpdateRequestDto dto, String isbn);

}
