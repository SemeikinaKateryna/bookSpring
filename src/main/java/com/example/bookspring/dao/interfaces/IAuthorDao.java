package com.example.bookspring.dao.interfaces;

import com.example.bookspring.entity.Author;

import java.util.List;

public interface IAuthorDao {
    Author findById(int id);
    public List<Author> findByBookId(int bookId);
    List<Author> findAll();
    boolean insert(Author author);
    boolean update(Author author);
    boolean delete(int id);
    public boolean deleteAuthorFromBook (int bookId, int authorId);
    public boolean addAuthorToBook(int bookId, int authorId);
}
