package com.example.bookspring.dao.interfaces;

import com.example.bookspring.entity.Book;
import com.example.bookspring.observer.Observable;

import java.util.List;

public interface IBookDao extends Observable {
    Book findById(int id);
    List<Book> findAll();
    boolean insert(Book object);
    boolean update(Book object);
    boolean delete(int id);
    Book findLast();
    List<Book> findAllByTitle(String param);
}
