package com.mon.bookstore.service.impl;

import com.mon.bookstore.dto.BookDto;
import com.mon.bookstore.dto.request.BookAddRequestDto;
import com.mon.bookstore.dto.request.BookUpdateRequestDto;
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
    public BookDto addBook(BookAddRequestDto dto) {
        // if book is already in store, don't create it - instead we increase the number available
        // if it is not in store, then we create it
        // we use the isbn, because it is a unique identifier for any particular book (or book version)
        Optional<Book> optionalBook = bookRepository.findByIsbn(dto.getIsbn());
        if (optionalBook.isPresent()) {
            throw new BookServiceException(String.format("This book with details [%s] already exists", dto));
        }

        Book newBook = new Book();
        BeanUtils.copyProperties(dto, newBook);

        Author author;
        String authorName = dto.getAuthor().getName();
        Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        if (optionalAuthor.isPresent()) {
            author = optionalAuthor.get();
        } else {
            author = new Author();
            author.setName(authorName);
        }

        newBook.setAuthor(author);
        newBook = bookRepository.save(newBook);

        return mapBookToDto(newBook);
    }

    @Override
    @Transactional
    public void deleteBook(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    @Override
    public List<BookDto> fetchAllAvailableBooks() {
        return bookRepository.fetchAllAvailableBooks().stream().map(this::mapBookToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> fetchAllBooks() {
        return bookRepository.findAll().stream().map(this::mapBookToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> fetchBookByIsbnLike(String isbn) {
        // TODO: add pagination
        List<Book> books = bookRepository.findByIsbnLikeIgnoreCase("%" + isbn + "%");
        return books.stream().map(this::mapBookToDto).toList();
    }

    @Override
    public List<BookDto> fetchBookByTitleLike(String title) {
        // TODO: add pagination
        List<Book> books = bookRepository.findByTitleLikeIgnoreCase("%" + title + "%");
        return books.stream().map(this::mapBookToDto).toList();
    }

    @Override
    public List<BookDto> getBooksByAuthor(String authorName) {
        // TODO: not currently implemented
        return null;
    }

    @Override
    public BookDto updateBookDetails(BookUpdateRequestDto dto, String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(String.format("book with this isbn [%s] cannot be found", isbn)));
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
        if (dto.getIsAvailable() != null) {
            book.setIsAvailable(dto.getIsAvailable());
        }
        bookRepository.save(book);
        return mapBookToDto(book);
    }

    private BookDto mapBookToDto(Book book) {
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        bookDto.setAuthor(book.getAuthor().getName());
        return bookDto;
    }
}
