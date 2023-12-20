package com.example.bookspring.observer.display;

import com.example.bookspring.entity.Book;
import com.example.bookspring.observer.Observer;

public class LoggingDisplayBook implements Observer<Book> {
    @Override
    public void insert(Book object) {
        System.out.println("New book was added, fields:"
                + " Title: " + object.getTitle() + " | Library:" + object.getLibrary().getAddress()
                + " Pages: " + object.getPages() + " | Year:" + object.getYear());
    }

    @Override
    public void update(Book object) {
        System.out.println("Book with id = " + object.getId() + " was updated, current fields:"
                + " Title: " + object.getTitle() + " | Library:" + object.getLibrary().getAddress()
                + " Pages: " + object.getPages() + " | Year:" + object.getYear());
    }

    @Override
    public void delete(Book object) {
        System.out.println("Book with id = " + object.getId() + " was deleted, past fields:"
                + " Title: " + object.getTitle() + " | Library:" + object.getLibrary().getAddress()
                + " Pages: " + object.getPages() + " | Year:" + object.getYear());
    }


}
