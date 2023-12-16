package com.mon.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Table
@Entity(name = "author")
@Data
public class Author {
    /**
     * A unique identifier for this author
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "author_id")
    private String id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Book> book = new HashSet<>();

    /**
     * The name of this author
     */
    @Column(unique = true)
    private String name;
}
