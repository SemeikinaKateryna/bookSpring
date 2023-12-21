package com.example.bookspring.mongodb.daos;

import com.example.bookspring.dao.interfaces.IAuthorDao;
import com.example.bookspring.entity.Author;
import com.example.bookspring.mongodb.docextractor.DocumentExtractor;
import com.example.bookspring.observer.Observer;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDbAuthorDao implements IAuthorDao, DocumentExtractor<Author> {
    private Author author;
    private final List<Observer<Author>> observers = new ArrayList<>();
    private final MongoCollection<Document> authorCollection;

    public MongoDbAuthorDao() {
        var mongoClient = MongoClients.create();
        var database = mongoClient.getDatabase("books");
        this.authorCollection = database.getCollection("author");
    }

    @Override
    public List<Author> findByBookId(int bookId) {
        List<Author> authors = new ArrayList<>();
        var query = new Document("bookId", bookId);
        try (MongoCursor<Document> cursor = authorCollection.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Author author = extractFromDocument(document);
                authors.add(author);
            }
        }
        return authors;
    }

    @Override
    public Author findById(int id) {
        var query = new Document("id", id);
        var document = authorCollection.find(query).first();

        if (document != null) {
            return extractFromDocument(document);
        }

        return null;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try (MongoCursor<Document> cursor = authorCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Author author = extractFromDocument(document);
                authors.add(author);
            }
        }
        return authors;
    }

    @Override
    public boolean update(Author author) {
        var query = new Document("id", author.getId());
        var update = new Document("$set", new Document("full_name", author.getFullName())
                .append("country", author.getCountry()));

        authorCollection.updateOne(query, update);
        return true;
    }

    @Override
    public boolean delete(int id) {
        var query = new Document("id", id);
        authorCollection.deleteOne(query);
        return true;
    }

    @Override
    public boolean deleteAuthorFromBook(int bookId, int authorId) {
        var query = Filters.and(Filters.eq("bookId", bookId), Filters.eq("id", authorId));
        authorCollection.deleteOne(query);
        return true;
    }

    @Override
    public boolean addAuthorToBook(int bookId, int authorId) {
        var document = new Document("id", authorId)
                .append("bookId", bookId);

        authorCollection.insertOne(document);
        return true;
    }

    @Override
    public boolean insert(Author author) {
        var document = new Document("id", author.getId())
                .append("full_name", author.getFullName())
                .append("country", author.getCountry());

        authorCollection.insertOne(document);
        return true;
    }

    public Author extractFromDocument(Document document) {
        return new Author.Builder(document.getInteger("id"))
                .addFullName(document.getString("full_name"))
                .addCountry(document.getString("country"))
                .build();
    }

    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    public void notifyObserversUpdate() {
        for (Observer<Author> observer : this.observers) {
            observer.update(author);
        }
    }

    public void notifyObserversInsert() {
        for (Observer<Author> observer : this.observers) {
            observer.insert(author);
        }
    }

    public void notifyObserversDelete() {
        for (Observer<Author> observer : this.observers) {
            observer.delete(author);
        }
    }
}
