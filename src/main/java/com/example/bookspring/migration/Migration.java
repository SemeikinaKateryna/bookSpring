package com.example.bookspring.migration;

public interface Migration {
    void migrateDataFromMySqlToMongoDb();
    void migrateDataFromMongoDbToMySql();
}
