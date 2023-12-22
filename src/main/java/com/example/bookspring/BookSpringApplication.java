package com.example.bookspring;

import com.example.bookspring.aggregation.AggregationQueries;
import com.example.bookspring.entity.Author;
import com.example.bookspring.entity.Book;
import com.example.bookspring.migration.DatabaseMigration;
import com.example.bookspring.migration.Migration;
import com.example.bookspring.mongodb.daos.MongoDbAuthorDao;
import com.example.bookspring.mongodb.daos.MongoDbBookDao;
import com.example.bookspring.mysql.daos.MySqlAuthorDao;
import com.example.bookspring.replication.MyReplicaSet;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookSpringApplication {
    public static void main(String[] args) {
            //SpringApplication.run(BookSpringApplication.class, args);

        MongoDbBookDao bookDao = new MongoDbBookDao();

        long startTime = System.currentTimeMillis();
        List<Document> resultWithoutAggreg = bookDao.getBooksContainingLetterT();
        long endTime = System.currentTimeMillis();
        System.out.println(resultWithoutAggreg);
        System.out.println("Час отримання даних з MongoDB без агрегації : " + (endTime - startTime) + " мс");


        AggregationQueries aggregationQueries = new AggregationQueries();
        long startTimeMongo = System.currentTimeMillis();
        AggregateIterable<Document> resultMongoDB = aggregationQueries.getBooksContainingLetterT();
        long endTimeMongo = System.currentTimeMillis();
        System.out.println(resultMongoDB);
        System.out.println("Час отримання даних з MongoDB з агрегацією: " + (endTimeMongo - startTimeMongo) + " мс");

    }


    public static void fillMongoDB(){
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("books");
        MongoCollection<Document> authorCollection = database.getCollection("book");


        for (int j = 0; j < 1000; j++) {
            Book book = new Book.Builder().addTitle("Title").addPages(534)
                    .addYear(1813).build();

            Document document = new Document()
                    .append("title", book.getTitle())
                    .append("pages", book.getPages())
                            .append("year", book.getYear());

            authorCollection.insertOne(document);
        }


    }



}
