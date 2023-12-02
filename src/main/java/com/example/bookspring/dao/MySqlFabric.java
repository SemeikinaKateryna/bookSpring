package com.example.bookspring.dao;

import com.example.bookspring.dao.interfaces.IAuthorDao;
import com.example.bookspring.dao.interfaces.IBookDao;
import com.example.bookspring.dao.interfaces.ILibraryDao;
import com.example.bookspring.mysql.daos.MySqlAuthorDao;
import com.example.bookspring.mysql.daos.MySqlBookDao;
import com.example.bookspring.mysql.daos.MySqlLibraryDao;

public class MySqlFabric implements IFabric {

    @Override
    public IBookDao createBook() {
        return new MySqlBookDao();
    }

    @Override
    public ILibraryDao createLibrary() {
        return new MySqlLibraryDao();
    }

    @Override
    public IAuthorDao createAuthor() {
        return new MySqlAuthorDao();
    }
}
