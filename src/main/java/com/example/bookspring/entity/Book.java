package com.example.bookspring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "number_of_pages", nullable = false)
    private Integer numberOfPages;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

}