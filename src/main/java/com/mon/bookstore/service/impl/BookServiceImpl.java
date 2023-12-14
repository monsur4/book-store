package com.mon.bookstore.service.impl;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookCreateRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
import com.mon.bookstore.dto.response.RetrievedBooksResponseDto;
import com.mon.bookstore.exceptions.BookNotFoundException;
import com.mon.bookstore.exceptions.BookServiceException;
import com.mon.bookstore.model.entity.Author;
import com.mon.bookstore.model.entity.Book;
import com.mon.bookstore.repository.AuthorRepository;
import com.mon.bookstore.repository.BookRepository;
import com.mon.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookDto addBook(BookCreateRequestDto dto) {
        // if book is already in store, don't create
        if (bookRepository.findByTitle(dto.getTitle()).isPresent())
            throw new BookServiceException("this book already exists in the store");

        Book book = new Book();
        BeanUtils.copyProperties(dto, book);
        book.setNumberAvailable(1);

        Author author;
        String authorName = dto.getAuthor().getName();
        Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        if (optionalAuthor.isPresent()) {
            author = optionalAuthor.get();
        } else {
            author = new Author();
            author.setName(authorName);
        }
//        author.getBook().add(book);
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);

        return mapBookToDto(savedBook);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapBookToDto).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("book with this id [%s] cannot be found", id)));
        return mapBookToDto(book);
    }

    @Override
    public List<BookDto> getBooksByAuthor(String authorName) {
        return null;
    }

    @Override
    public BookDto getBookIsbn(String isbn) {
        return null;
    }

    @Override
    public BookDto updateBookDetails(BookUpdateRequestDto dto, String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException(String.format("book with this title [%s] cannot be found", title)));
        if (dto.getTitle() != null) {
            book.setTitle(dto.getTitle());
        }
        if (dto.getPageCount() != null) {
            book.setPageCount(dto.getPageCount());
        }
        if (dto.getIsbn() != null) {
            book.setIsbn(dto.getIsbn());
        }
        if (dto.getAuthor() != null && dto.getAuthor().getName() != null) {
            String authorName = dto.getAuthor().getName();
            Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
            Author author;
            if (optionalAuthor.isPresent()) {
                author = optionalAuthor.get();
            } else {
                author = new Author();
                author.setName(authorName);
            }
            author.getBook().add(book);
            book.setAuthor(author);
        }
        if(dto.getNumberToAdd() != null){
            book.setNumberAvailable(book.getNumberAvailable() + dto.getNumberToAdd());
        }
        bookRepository.save(book);
        return mapBookToDto(book);
    }

    @Override
    @Transactional
    public void deleteBookByTitle(String title) {
        bookRepository.deleteByTitle(title);
    }

    private BookDto mapBookToDto(Book book) {
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        bookDto.setAuthor(book.getAuthor().getName());
        bookDto.setNumberAvailable(book.getNumberAvailable());
        return bookDto;
    }

    @Override
    public RetrievedBooksResponseDto retrieveBooks(String title, Integer number) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException(String.format("there is no book with title [%s] in store", title)));
        if (book.getNumberAvailable() < number){
            throw new BookServiceException(String.format("there is not enough books with title [%s] to retrieve", title));
        }
        Integer numberRemaining = book.getNumberAvailable() - number;
        book.setNumberAvailable(numberRemaining);
        bookRepository.save(book);
        return RetrievedBooksResponseDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor().getName())
                .isbn(book.getIsbn())
                .numberRetrieved(number)
                .numberRemaining(numberRemaining)
                .build();
    }

    @Override
    public BookDto getBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException(String.format("book with this title [%s] cannot be found", title)));
        return mapBookToDto(book);
    }
}
