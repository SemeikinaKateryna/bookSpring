package com.example.bookspring.dao;

import com.example.bookspring.dao.interfaces.IAuthorDao;
import com.example.bookspring.dao.interfaces.IBookDao;
import com.example.bookspring.dao.interfaces.ILibraryDao;

public interface IFabric {
    IBookDao createBook();
    ILibraryDao createLibrary();
    IAuthorDao createAuthor();
}
