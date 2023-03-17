package com.example.bookspring.repository;

import com.example.bookspring.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Integer> {
}