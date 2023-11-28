package com.example.bookspring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Book {
    private Integer id;
    private Library library;
    private String title;
    private Integer pages;
    private Integer year;
    private List<Author> author = new ArrayList<>();

}