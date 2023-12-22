package com.example.bookspring.aggregation;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import java.util.Arrays;

public class AggregationQueries {

    public Integer countAuthorsByName(String name) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("books");
        MongoCollection<Document> authorCollection = database.getCollection("author");

        AggregateIterable<Document> aggregateResult =
                authorCollection
                        .aggregate(Arrays.asList(
                                Aggregates.match(Filters.regex("full_name", "^" + name)),
                                Aggregates.group("$full_name", Accumulators.sum("amount", 1))
                        ));

        MongoCursor<Document> cursor = aggregateResult.iterator();
        int count = 0;
        while (cursor.hasNext()) {
            cursor.next();
            count++;
        }
        cursor.close();
        return count;
    }

    public AggregateIterable<Document> countAuthorsByCountry() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("books");
        MongoCollection<Document> authorCollection = database.getCollection("author");
        AggregateIterable<Document> aggregateResult = authorCollection.aggregate(Arrays.asList(
                Aggregates.group("$country", Accumulators.sum("authorCount", 1))
        ));
        return aggregateResult;
    }

    public AggregateIterable<Document> printAuthorsByCountryAndNameStartWith(String country) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("books");
        MongoCollection<Document> authorCollection = database.getCollection("author");
        AggregateIterable<Document> aggregateResult = authorCollection.aggregate(Arrays.asList(
                Aggregates.match(Filters.and(
                        Filters.eq("country", "country"),
                        Filters.regex("full_name", "^John")
                )),
                Aggregates.group("$country", Accumulators.sum("authorCount", 1))
        ));
        return aggregateResult;
    }

    public AggregateIterable<Document> countBooksByYear(int year) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("books");
        MongoCollection<Document> bookCollection = database.getCollection("book");
        return bookCollection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("year", year)),
                Aggregates.group("$year", Accumulators.sum("bookCount", 1))
        ));
    }
    public AggregateIterable<Document> getBooksContainingLetterT() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("books");
        MongoCollection<Document> bookCollection = database.getCollection("book");
        return bookCollection.aggregate(Arrays.asList(
                Aggregates.match(Filters.regex("title", "T", "i")), // "i" - ігнорує регістр
                Aggregates.project(Projections.fields(
                        Projections.include("title"),
                        Projections.include("pages"),
                        Projections.include("year"),
                        Projections.excludeId()
                ))
        ));
    }

}