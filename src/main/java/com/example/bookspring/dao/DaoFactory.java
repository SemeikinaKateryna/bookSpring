package com.example.bookspring.dao;


import com.example.bookspring.entity.Book;
import com.example.bookspring.mysql.DatabaseConnection;
import com.example.bookspring.mysql.daos.MySqlBookDao;


public class DaoFactory {


    // Використання паттерну Singleton
    private static IDao<Book> instance = null;
    private DaoFactory() {
    }

    // Фабричний метод
    public static IDao<Book> getDAOInstance(TypeDao type) {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    switch (type) {
                        case MY_SQL:
                            instance = new MySqlBookDao();
                            break;
                        case TEXT_FILE:
                            // Implement text file DAO instance if needed
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported DAO type: " + type);
                    }
                }
            }
        }
        return instance;
    }
}

