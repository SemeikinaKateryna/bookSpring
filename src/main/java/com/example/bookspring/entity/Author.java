package com.example.bookspring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Author {
    private Integer id;
    private String fullName;
    private String country;
}
