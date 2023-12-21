package com.example.bookspring.mongodb.docextractor;

import org.bson.Document;

public interface DocumentExtractor<T> {
    T extractFromDocument(Document document);
}
