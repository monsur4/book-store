package com.mon.bookstore.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mon.bookstore.dto.request.BookAddRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
import com.mon.bookstore.model.entity.Author;
import com.mon.bookstore.model.entity.Book;
import com.mon.bookstore.repository.AuthorRepository;
import com.mon.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.Mockito.*;

class BookServiceImplTest {

    static ObjectMapper objectMapper = new ObjectMapper();
    BookRepository bookRepository;
    AuthorRepository authorRepository;

    BookServiceImpl bookService;

    @BeforeEach
    public void setup(){
        bookRepository = mock(BookRepository.class);
        authorRepository = mock(AuthorRepository.class);
        bookService = new BookServiceImpl(bookRepository, authorRepository);
    }

    @Test
    public void whenAddBook_BookIsSaved() throws IOException, URISyntaxException {
        // given
        String bookAddRequestDtoString = readJsonAsString("add-book-request-valid.json");
        BookAddRequestDto dto = objectMapper.readValue(bookAddRequestDtoString, BookAddRequestDto.class);
        Book book = new Book();
        BeanUtils.copyProperties(dto, book);
        Author author = new Author();
        author.setName(dto.getAuthor().getName());
        book.setAuthor(author);

        // when
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.empty());
        when(authorRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.addBook(dto);

        // then
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void whenUpdateBookWithValidIsbn_BookIsUpdated() throws URISyntaxException, IOException {
        // given
        String bookUpdateRequestDtoString = readJsonAsString("update-book-request-valid.json");
        BookUpdateRequestDto dto = objectMapper.readValue(bookUpdateRequestDtoString, BookUpdateRequestDto.class);
        String isbn = "11112222";
        Book book = new Book();
        BeanUtils.copyProperties(dto, book);
        Author author = new Author();
        author.setName(dto.getAuthor().getName());
        book.setAuthor(author);

        // when
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(book));
        when(authorRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.updateBookDetails(dto, isbn);

        // then
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    private static String readJsonAsString(String fileName) throws URISyntaxException, IOException {
        URL resource = BookServiceImplTest.class.getClassLoader().getResource(fileName);
        return Files.readString(Paths.get(resource.toURI()));
    }

}