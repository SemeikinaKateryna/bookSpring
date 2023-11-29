package com.example.bookspring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Book {
    private Integer id;
    private Library library;
    private String title;
    private Integer pages;
    private Integer year;
    private List<Author> author = new ArrayList<>();


    // Приватний конструктор, щоб можна було створювати об'єкти лише за допомогою Builder
    private Book() {
    }

    // Внутрішній статичний клас Builder
    public static class Builder {
        private Book book;

        public Builder() {
            book = new Book();
        }

        public Builder(Integer id) {
            book = new Book();
            book.setId(id);
        }

        public Builder addTitle(String title) {
            book.setTitle(title);
            return this;
        }

        public Builder addLibrary(Library library) {
            book.setLibrary(library);
            return this;
        }

        public Builder addPages(Integer pages) {
            book.setPages(pages);
            return this;
        }

        public Builder addYear(Integer year) {
            book.setYear(year);
            return this;
        }

        public Builder addAuthor(Author author) {
            book.getAuthor().add(author);
            return this;
        }

        // Метод для побудови об'єкта Book
        public Book build() {
            return book;
        }
    }

}