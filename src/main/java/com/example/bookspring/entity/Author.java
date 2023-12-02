package com.example.bookspring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Author {
    private Integer id;
    private String fullName;
    private String country;

    // Приватний конструктор, щоб можна було створювати об'єкти лише за допомогою Builder
    private Author() {
    }

    // Внутрішній статичний клас Builder
    public static class Builder {
        private final Author author;

        public Builder() {
            author = new Author();
        }

        public Builder(Integer id) {
            author = new Author();
            author.setId(id);
        }

        public Builder addFullName(String fullName) {
            author.setFullName(fullName);
            return this;
        }

        public Builder addCountry(String country) {
            author.setCountry(country);
            return this;
        }

        // Метод для побудови об'єкта Author
        public Author build() {
            return author;
        }
    }
}
