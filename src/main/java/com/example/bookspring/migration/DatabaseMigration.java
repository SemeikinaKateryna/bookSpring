package com.example.bookspring.migration;

import com.example.bookspring.dao.IFabric;
import com.example.bookspring.dao.MongoDBFabric;
import com.example.bookspring.dao.MySqlFabric;
import com.example.bookspring.dao.interfaces.IAuthorDao;
import com.example.bookspring.dao.interfaces.IBookDao;
import com.example.bookspring.dao.interfaces.ILibraryDao;
import com.example.bookspring.entity.Author;
import com.example.bookspring.entity.Book;
import com.example.bookspring.entity.BookAuthor;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mysql.DatabaseConnection;
import com.example.bookspring.mysql.queries.MySQLQuery;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMigration implements Migration{
    private final IBookDao bookDaoMySql;
    private final IAuthorDao authorDaoMySql;
    private final ILibraryDao libraryDaoMySql;

    private final IBookDao bookDaoMongoDb;
    private final IAuthorDao authorDaoMongoDb;
    private final ILibraryDao libraryDaoMongoDb;

    public DatabaseMigration() {
        IFabric fabricMySql = new MySqlFabric();
        IFabric fabricMongoDb = new MongoDBFabric();

        this.bookDaoMySql = fabricMySql.createBook();
        this.authorDaoMySql = fabricMySql.createAuthor();
        this.libraryDaoMySql = fabricMySql.createLibrary();

        this.bookDaoMongoDb = fabricMongoDb.createBook();
        this.authorDaoMongoDb = fabricMongoDb.createAuthor();
        this.libraryDaoMongoDb = fabricMongoDb.createLibrary();
    }

    @Override
    public void migrateDataFromMySqlToMongoDb() {
        clearCollectionsMongoDB();

        List<Book> books = bookDaoMySql.findAll();
        List<Author> authors = authorDaoMySql.findAll();
        List<Library> libraries = libraryDaoMySql.findAll();
        List<BookAuthor> booksAuthors = new ArrayList<>();


        for (Book b : books) {
            List<Author> authorsByBook = b.getAuthor();
            for ( Author a: authorsByBook) {
                BookAuthor bookAuthor = new BookAuthor();
                bookAuthor.setAuthor(a);
                bookAuthor.setBook(b);
                booksAuthors.add(bookAuthor);
            }
        }


        for (Book book : books) {
            bookDaoMongoDb.insert(book);
        }

        for (Author author: authors) {
            authorDaoMongoDb.insert(author);
        }

        for(Library library: libraries){
            libraryDaoMongoDb.insert(library);
        }

        for (BookAuthor ba: booksAuthors) {
            authorDaoMongoDb.addAuthorToBook(ba.getBook().getId(), ba.getAuthor().getId());
        }
    }


    @Override
    public void migrateDataFromMongoDbToMySql() {
        clearTablesMySQL();

        List<Book> books = bookDaoMongoDb.findAll();
        List<Author> authors = authorDaoMongoDb.findAll();
        List<Library> libraries = libraryDaoMongoDb.findAll();
        List<BookAuthor> booksAuthors = new ArrayList<>();


        for (Book b : books) {
            List<Author> authorsByBook = b.getAuthor();
            for ( Author a: authorsByBook) {
                BookAuthor bookAuthor = new BookAuthor();
                bookAuthor.setAuthor(a);
                bookAuthor.setBook(b);
                booksAuthors.add(bookAuthor);
            }
        }

        for(Library library: libraries){
            libraryDaoMySql.insert(library);
        }

        for (Book book : books) {
            bookDaoMySql.insert(book);
        }

        for (Author author: authors) {
            authorDaoMySql.insert(author);
        }

        for (BookAuthor ba: booksAuthors) {
            authorDaoMySql.addAuthorToBook(ba.getBook().getId(), ba.getAuthor().getId());
        }
    }

    private static void clearCollectionsMongoDB(){
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("books");
        database.getCollection("book").drop();
        database.getCollection("author").drop();
        database.getCollection("library").drop();
    }

    private static void clearTablesMySQL(){
        try(Connection connection = DatabaseConnection.getConnection()){
            assert connection != null;
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate(MySQLQuery.FOREIGN_KEY_SET_NULL);
            statement = connection.createStatement();
            statement.executeUpdate(MySQLQuery.LibraryRequestsSQL.DELETE_FROM_LIBRARY);
            statement = connection.createStatement();
            statement.executeUpdate(MySQLQuery.BookRequestsSQL.DELETE_FROM_BOOK);
            statement = connection.createStatement();
            statement.executeUpdate(MySQLQuery.AuthorRequestsSQL.DELETE_FROM_AUTHOR);
            statement = connection.createStatement();
            statement.executeUpdate(MySQLQuery.BookAuthorRequestsSQL.DELETE_FROM_BOOK_AUTHOR);
            statement = connection.createStatement();
            statement.executeUpdate(MySQLQuery.FOREIGN_KEY_SET_ONE);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
