package com.example.bookspring.repository;

import com.example.bookspring.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByTitleOrderByAuthorAsc(String title);

}