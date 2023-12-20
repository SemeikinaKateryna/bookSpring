package com.example.bookspring.observer.display;

import com.example.bookspring.entity.Author;
import com.example.bookspring.observer.Observer;

public class LoggingDisplayAuthor implements Observer<Author> {
    @Override
    public void insert(Author object) {
        System.out.println("New author was added, fields:"
                + " Full name: " + object.getFullName() + " | Country:" + object.getCountry());
    }

    @Override
    public void update(Author object) {
        System.out.println("Author with id = " + object.getId() + " was updated, current fields:"
                + " Full name: " + object.getFullName() + " | Country:" + object.getCountry());
    }

    @Override
    public void delete(Author object) {
        System.out.println("Author with id = " + object.getId() + " was deleted, past fields:"
                + " Full name: " + object.getFullName() + " | Country:" + object.getCountry());
    }
}
