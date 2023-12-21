package com.example.bookspring.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookAuthor {
    Book book;
    Author author;
}
