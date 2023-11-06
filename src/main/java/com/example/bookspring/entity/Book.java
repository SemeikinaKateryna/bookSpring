package com.example.bookspring.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private Integer id;

    private String title;

    private String author;

    private Integer numberOfPages;

    private Library library;

}