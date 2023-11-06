package com.example.bookspring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class Library {
    private Integer id;

    private String address;

    private Set<Book> books = new LinkedHashSet<>();

}