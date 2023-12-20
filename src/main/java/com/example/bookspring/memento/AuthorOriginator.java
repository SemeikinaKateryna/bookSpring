package com.example.bookspring.memento;

import com.example.bookspring.entity.Author;

public class AuthorOriginator {
    private Author author;

    public void set(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public Memento takeSnapshot() {
        return new Memento(this.author);
    }

    public void restore(Memento memento) {
        this.author = memento != null ? memento.getSavedAuthor() : null;
    }

    public static class Memento {
        private final Author author;

        private Memento(Author author) {
            this.author = author;
        }

        private Author getSavedAuthor() {
            return author;
        }
    }
}
