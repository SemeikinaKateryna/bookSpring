package com.example.bookspring.proxy;

import com.example.bookspring.dao.interfaces.IAuthorDao;
import com.example.bookspring.entity.Author;
import com.example.bookspring.mysql.daos.MySqlAuthorDao;
import com.example.bookspring.proxy.interfaceProxy.FindAll;

import java.util.ArrayList;
import java.util.List;

public class AuthorDaoProxy implements FindAll<Author> {
    private final IAuthorDao mySqlAuthorDao;
    private final List<Author> cache;

    public AuthorDaoProxy() {
        this.mySqlAuthorDao = new MySqlAuthorDao();
        this.cache = new ArrayList<>();
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = cache;
        if(!authors.isEmpty()){
            System.out.println("Authors loaded from cache.");
        }
        if (authors.isEmpty()) {
            authors = mySqlAuthorDao.findAll();
            if(authors != null){
                cache.addAll(authors);
            }
            System.out.println("Authors loaded from db.");
        }
        return authors;
    }

    public void reset() {
        cache.clear();
    }
}
