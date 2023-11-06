package com.example.bookspring.dao;


import com.example.bookspring.entity.Book;

import java.sql.SQLException;

public class DaoFactory {

    // Використання паттерну Singleton
    private static IDao<Book> instance = null;

    // Фабричний метод
    public static IDao<Book> getDAOInstance(TypeDao type) {
        if(instance == null) {
            switch (type) {
                case MY_SQL:
                    instance = new MySqlBookDao();
                    break;
                case JSON:

                    break;
                default:
                    instance = null;
                    break;
            }
        }
            return instance;
    }

}
