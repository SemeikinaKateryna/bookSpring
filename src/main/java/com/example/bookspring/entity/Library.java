package com.example.bookspring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id", nullable = false)
    private Integer id;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @OneToMany(mappedBy = "library")
    private Set<Book> books = new LinkedHashSet<>();

}