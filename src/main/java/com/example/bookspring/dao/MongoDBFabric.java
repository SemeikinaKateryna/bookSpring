package com.example.bookspring.dao;

import com.example.bookspring.dao.interfaces.IAuthorDao;
import com.example.bookspring.dao.interfaces.IBookDao;
import com.example.bookspring.dao.interfaces.ILibraryDao;
import com.example.bookspring.mongodb.daos.MongoDbAuthorDao;
import com.example.bookspring.mongodb.daos.MongoDbBookDao;
import com.example.bookspring.mongodb.daos.MongoDbLibraryDao;

public class MongoDBFabric implements IFabric{
    @Override
    public IBookDao createBook() {
        return new MongoDbBookDao();
    }

    @Override
    public ILibraryDao createLibrary() {
        return new MongoDbLibraryDao();
    }

    @Override
    public IAuthorDao createAuthor() {
        return new MongoDbAuthorDao();
    }
}
