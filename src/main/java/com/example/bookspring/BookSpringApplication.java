package com.example.bookspring;

import com.example.bookspring.dao.DaoFactory;
import com.example.bookspring.dao.IDao;
import com.example.bookspring.dao.TypeDao;
import com.example.bookspring.entity.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class BookSpringApplication {
    public static void main(String[] args) {
       IDao<Book> daoInstance = DaoFactory.getDAOInstance(TypeDao.MY_SQL);

        SpringApplication.run(BookSpringApplication.class, args);
    }

}
