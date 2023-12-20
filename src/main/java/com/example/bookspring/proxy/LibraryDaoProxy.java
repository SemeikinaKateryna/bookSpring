package com.example.bookspring.proxy;


import com.example.bookspring.dao.interfaces.ILibraryDao;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mysql.daos.MySqlLibraryDao;
import com.example.bookspring.proxy.interfaceProxy.FindAll;

import java.util.ArrayList;
import java.util.List;

public class LibraryDaoProxy implements FindAll<Library> {
    private final ILibraryDao mySqlLibraryDao;
    private final List<Library> cache;

    public LibraryDaoProxy() {
        this.mySqlLibraryDao = new MySqlLibraryDao();
        this.cache = new ArrayList<>();
    }

    @Override
    public List<Library> findAll() {
        List<Library> libraries = cache;
        if(!libraries.isEmpty()){
            System.out.println("Libraries loaded from cache.");
        }
        if (libraries.isEmpty()) {
            libraries = mySqlLibraryDao.findAll();
            if(libraries != null){
                cache.addAll(libraries);
            }
            System.out.println("Libraries loaded from db.");
        }
        return libraries;
    }

    public void reset() {
        cache.clear();
    }
}
