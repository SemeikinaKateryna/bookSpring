package com.example.bookspring;

import com.example.bookspring.migration.DatabaseMigration;
import com.example.bookspring.migration.Migration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookSpringApplication {
    public static void main(String[] args) {
            //SpringApplication.run(BookSpringApplication.class, args);

        Migration migration = new DatabaseMigration();
        migration.migrateDataFromMySqlToMongoDb();
    }
}
