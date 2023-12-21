package com.example.bookspring.mongodb.daos;

import com.example.bookspring.dao.interfaces.ILibraryDao;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mongodb.docextractor.DocumentExtractor;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDbLibraryDao implements ILibraryDao, DocumentExtractor<Library> {
    private final MongoCollection<Document> libraryCollection;

    public MongoDbLibraryDao() {
        var mongoClient = MongoClients.create();
        var database = mongoClient.getDatabase("books");
        this.libraryCollection = database.getCollection("library");
    }

    @Override
    public Library findById(int id) {
        var query = new Document("id", id);
        var document = libraryCollection.find(query).first();

        if (document != null) {
            return extractFromDocument(document);
        }

        return null;
    }

    @Override
    public List<Library> findAll() {
        List<Library> libraries = new ArrayList<>();
        try (var cursor = libraryCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Library library = extractFromDocument(document);
                libraries.add(library);
            }
        }
        return libraries;
    }

    @Override
    public boolean insert(Library library) {
        var document = new Document("id", library.getId())
                .append("address", library.getAddress());

        libraryCollection.insertOne(document);
        return true;
    }

    @Override
    public boolean delete(int id) {
        var query = new Document("id", id);
        libraryCollection.deleteOne(query);
        return true;
    }

    public Library extractFromDocument(Document document) {
        return new Library.Builder(document.getInteger("id"))
                .addAddress(document.getString("address"))
                .build();

    }
}
