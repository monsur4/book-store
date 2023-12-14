package com.mon.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity(name = "book")
@Data
public class Book {

    /**
     * A unique identifier for this book
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The title of this book
     */
    @Column(unique = true)
    private String title;

    /**
     * The number of pages
     */
    private Integer pageCount;

    /**
     * The author
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    /**
     * The isbn
     */
    private String isbn;

    /**
     * The number of this book available in the store
     */
    private Integer numberAvailable = 0;
}
