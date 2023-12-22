package com.example.bookspring.mongodb.daos;

import com.example.bookspring.dao.interfaces.IBookDao;
import com.example.bookspring.entity.Book;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mongodb.docextractor.DocumentExtractor;

import com.example.bookspring.observer.Observer;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class MongoDbBookDao implements IBookDao, DocumentExtractor<Book> {
    private Book book;
    private final List<Observer<Book>> observers = new ArrayList<>();
    private final MongoCollection<Document> bookCollection;

    public MongoDbBookDao() {
        var mongoClient = MongoClients.create();
        var database = mongoClient.getDatabase("books");
        this.bookCollection = database.getCollection("book");
    }

    @Override
    public Book findById(int id) {
        var query = new Document("id", id);
        var document = bookCollection.find(query).first();

        if (document != null) {
            return extractFromDocument(document);
        }

        return null;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (MongoCursor<Document> cursor = bookCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Book book = extractFromDocument(document);
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<Book> findAllByTitle(String param) {
        List<Book> books = new ArrayList<>();
        var query = new Document("title", java.util.regex.Pattern.compile(param, Pattern.CASE_INSENSITIVE));
        try (MongoCursor<Document> cursor = bookCollection.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Book book = extractFromDocument(document);
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public boolean insert(Book book) {
        var document = new Document("id", book.getId())
                .append("library_id", book.getLibrary().getId())
                .append("title", book.getTitle())
                .append("pages", book.getPages())
                .append("year", book.getYear());

        bookCollection.insertOne(document);
        return true;
    }

    @Override
    public boolean update(Book book) {
        var query = new Document("id", book.getId());
        var update = new Document("$set", new Document("library_id", book.getLibrary().getId())
                .append("title", book.getTitle())
                .append("pages", book.getPages())
                .append("year", book.getYear()));

        bookCollection.updateOne(query, update);
        return true;
    }

    @Override
    public boolean delete(int id) {
        var query = new Document("id", id);
        bookCollection.deleteOne(query);
        return true;
    }

    @Override
    public Book findLast() {
        Document lastDocument = bookCollection.find().sort(new Document("_id", -1)).first();

        if (lastDocument != null) {
            return extractFromDocument(lastDocument);
        }

        return null;
    }


     public Book extractFromDocument(Document document) {
        return new Book.Builder(document.getInteger("id"))
                .addTitle(document.getString("title"))
                .addLibrary(new Library.Builder(document.getInteger("library_id")).build())
                .addPages(document.getInteger("pages"))
                .addYear(document.getInteger("year"))
                .build();
    }

    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    public void notifyObserversUpdate() {
        for (Observer<Book> observer : this.observers) {
            observer.update(book);
        }
    }

    public void notifyObserversInsert() {
        for (Observer<Book> observer : this.observers) {
            observer.insert(book);
        }
    }

    public void notifyObserversDelete() {
        for (Observer<Book> observer : this.observers) {
            observer.delete(book);
        }
    }

    public long countBooksByYear(int year) {
        return bookCollection.countDocuments(Filters.eq("year", year));
    }

    public List<Document> getBooksContainingLetterT() {
        FindIterable<Document> result = bookCollection.find(Filters.regex("title", "T", "i")) // "i" - ігнорує регістр
                .projection(Projections.fields(
                        Projections.include("title"),
                        Projections.include("pages"),
                        Projections.include("year"),
                        Projections.excludeId()
                ));

        return toList(result);
    }

    // Допоміжний метод для перетворення FindIterable в List<Document>
    private List<Document> toList(FindIterable<Document> iterable) {
        List<Document> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }
}
