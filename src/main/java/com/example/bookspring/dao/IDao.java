package com.example.bookspring.dao;

import com.example.bookspring.entity.Book;
import com.example.bookspring.mysql.daos.Searchable;

import java.util.List;

public interface IDao<T> extends Searchable<T> {
    T findById(int id);
    List<T> findAll();
    boolean insert(T object);
    boolean update(T object);
    boolean delete(int id);

    Book findLast();
}
