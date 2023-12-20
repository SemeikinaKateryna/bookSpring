package com.example.bookspring.memento;

import com.example.bookspring.entity.Author;

import java.util.Deque;
import java.util.LinkedList;

public class Caretaker {
    private final AuthorOriginator authorOriginator;
    private final Deque<AuthorOriginator.Memento> stateHistory;

    public Caretaker() {
        authorOriginator = new AuthorOriginator();
        stateHistory = new LinkedList<>();
    }

    public void write(Author author) {
        authorOriginator.set(author);
        stateHistory.offer(authorOriginator.takeSnapshot());
    }

    public void undo() {
        if (stateHistory.isEmpty()) {
            return;
        }
        stateHistory.pollLast();
        authorOriginator.restore(stateHistory.peekLast());
    }

    public AuthorOriginator getAuthorOriginator() {
        return authorOriginator;
    }
}
