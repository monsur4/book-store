package com.mon.bookstore.repository;

import com.mon.bookstore.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByTitleLikeIgnoreCase(String title);

    void deleteByIsbn(String isbn);

    List<Book> findByIsbnLikeIgnoreCase(String isbn);

    Optional<Book> findByIsbn(String isbn);

    @Query(value = "SELECT * FROM book WHERE is_available=true", nativeQuery = true)
    List<Book> fetchAllAvailableBooks();
}
