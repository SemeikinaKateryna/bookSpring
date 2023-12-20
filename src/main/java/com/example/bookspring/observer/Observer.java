package com.example.bookspring.observer;

public interface Observer<T> {
    void insert(T object);
    void update(T object);
    void delete(T object);

}