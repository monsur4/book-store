package com.mon.bookstore.repository;

import com.mon.bookstore.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findByName(String name);
}
