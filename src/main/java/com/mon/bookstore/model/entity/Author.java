package com.mon.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "author")
@Data
public class Author {
    /**
     * A unique identifier for this author
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The name of this author
     */
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> book = new HashSet<>();
}
