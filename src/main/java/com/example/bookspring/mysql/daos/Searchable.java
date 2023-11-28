package com.example.bookspring.mysql.daos;

import java.util.List;

public interface Searchable<T> {
    List<T> findAllByTitle(String param);
}
