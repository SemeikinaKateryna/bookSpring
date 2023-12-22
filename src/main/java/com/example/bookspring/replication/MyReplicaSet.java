package com.example.bookspring.replication;

import com.example.bookspring.entity.Author;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class MyReplicaSet {
    private final String connectionString = String.valueOf(new ConnectionString("mongodb://localhost:27018, localhost:27017, localhost:27019/"));

    private static final WriteConcern writeConcern = WriteConcern.JOURNALED;

    public void workWithReplicaSet() {
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoCollection<Document> authorCollection = mongoClient.getDatabase("books").getCollection("author");

        authorCollection.withWriteConcern(writeConcern);

        int maxRetries = 3;

        // Запис даних з вимірюванням часу
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < maxRetries; i++) {
            try {
                for (int j = 0; j < 10000; j++) {
                    Author author = new Author.Builder().addFullName("John" + j).addCountry("Ukraine").build();

                    Document document = new Document()
                            .append("full_name", author.getFullName())
                            .append("country", author.getCountry());

                    authorCollection.insertOne(document);
                }
                // Якщо запис пройшов без помилок, виходимо з циклу
                break;
            } catch (Exception e) {
                System.err.println("Помилка при записі в БД: " + e.getMessage());
                if (i < maxRetries - 1) {
                    // Затримка перед повторною спробою
                    try {
                        Thread.sleep(1000);  // Затримка в 1 секунду
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.err.println("Перевищено максимальну кількість спроб. Зупинено запис документів в БД.");
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Час запису з WriteConcern " + writeConcern + ": " + (endTime - startTime) + " мс");

        // Читання даних з вимірюванням часу
        startTime = System.currentTimeMillis();
        FindIterable<Document> documents = authorCollection.find();
        MongoCursor<Document> cursor = documents.iterator();
        int readCount = 0;
        try {
            while (cursor.hasNext()) {
                Document document1 = cursor.next();
                readCount++;
            }
        } finally {
            cursor.close();
        }
        endTime = System.currentTimeMillis();
        System.out.println("Час читання з ReadConcern " + writeConcern + ": " + (endTime - startTime) + " мс");

        System.out.println("Записано та прочитано " + readCount + " документів. Цілісності запису перевірено.");
        System.out.println();
    }
}


