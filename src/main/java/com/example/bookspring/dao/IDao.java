package com.example.bookspring.dao;

import java.util.List;

public interface IDao<T> {
    T findById(int id);
    List<T> findAll();
    boolean insert(T object);
    boolean update(T object);
    boolean delete(int id);
}
