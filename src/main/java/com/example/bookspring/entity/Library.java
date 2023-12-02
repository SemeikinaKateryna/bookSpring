package com.example.bookspring.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Library {
    private Integer id;

    private String address;

    // Приватний конструктор, щоб можна було створювати об'єкти лише за допомогою Builder

    private Library() {
    }

    // Внутрішній статичний клас Builder
    public static class Builder {
        private final Library library;

        public Builder() {
            library = new Library();
        }

        public Builder(Integer id) {
            library = new Library();
            library.setId(id);
        }

        public Builder addAddress(String address) {
            library.setAddress(address);
            return this;
        }

        // Метод для побудови об'єкта Library
        public Library build() {
            return library;
        }
    }
}