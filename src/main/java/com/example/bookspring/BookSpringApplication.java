package com.example.bookspring;

import com.example.bookspring.migration.DatabaseMigration;
import com.example.bookspring.migration.Migration;
import com.example.bookspring.replication.MyReplicaSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookSpringApplication {
    public static void main(String[] args) {
            //SpringApplication.run(BookSpringApplication.class, args);

        MyReplicaSet replicaSet = new MyReplicaSet();
        replicaSet.workWithReplicaSet();
    }
}
