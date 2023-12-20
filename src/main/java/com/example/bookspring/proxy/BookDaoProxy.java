package com.example.bookspring.proxy;

import com.example.bookspring.dao.interfaces.IBookDao;
import com.example.bookspring.entity.Book;
import com.example.bookspring.mysql.daos.MySqlBookDao;
import com.example.bookspring.proxy.interfaceProxy.FindAll;

import java.util.ArrayList;
import java.util.List;

public class BookDaoProxy implements FindAll<Book> {
    private final IBookDao mySqlBookDao;
    private final List<Book> cache;

    public BookDaoProxy() {
        this.mySqlBookDao = new MySqlBookDao();
        this.cache = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = cache;
        if(!books.isEmpty()){
            System.out.println("Books loaded from cache.");
        }
        if (books.isEmpty()) {
            books = mySqlBookDao.findAll();
            if(books != null){
                cache.addAll(books);
            }
            System.out.println("Books from db.");
        }
        return books;
    }

    public void reset() {
        cache.clear();
    }
}
