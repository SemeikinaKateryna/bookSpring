package com.example.bookspring.dao.interfaces;

import com.example.bookspring.entity.Library;
import com.example.bookspring.proxy.interfaceProxy.FindAll;

import java.util.List;

public interface ILibraryDao extends FindAll<Library> {
    Library findById(int id);
    List<Library> findAll();
    boolean insert(Library object);
    boolean delete(int id);
}
