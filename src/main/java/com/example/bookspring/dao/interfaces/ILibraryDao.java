package com.example.bookspring.dao.interfaces;

import com.example.bookspring.entity.Library;

import java.util.List;

public interface ILibraryDao {
    Library findById(int id);
    List<Library> findAll();
    boolean insert(Library object);
    boolean delete(int id);
}
