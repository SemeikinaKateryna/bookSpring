package com.example.bookspring.dao;

import com.example.bookspring.entity.Book;
import com.example.bookspring.mysql.LibraryDao;
import com.example.bookspring.mysql.MySQLQuery;
import com.example.bookspring.mysql.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
//@PropertySource("application.properties")
public class MySqlBookDao implements IDao<Book>, ResultSetExtractor<Book> {
    //@Value("${spring.datasource.url}")
    private final static String URL = "jdbc:mysql://localhost:3306/books";
    //@Value("${spring.datasource.username}")
    private final static String USERNAME = "root";
    //@Value("${spring.datasource.password}")
    private final static String PASSWORD = "Katya";

    private final LibraryDao libraryDao = new LibraryDao();

    public MySqlBookDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException |
                 NoSuchMethodException | InvocationTargetException ex) {
            System.out.println("SQLState: " + ex);
            System.out.println("VendorError: " + ex);
        }
    }

    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public Book findById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.FIND_BY_ID_BOOK)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.SELECT_ALL_BOOK)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = extractFromResultSet(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public boolean insert(Book object) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.INSERT_BOOK)) {
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getAuthor());
            statement.setInt(3, object.getNumberOfPages());
            statement.setInt(4, object.getLibrary().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Book object) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.UPDATE_BOOK)) {
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getAuthor());
            statement.setInt(3, object.getNumberOfPages());
            statement.setInt(4, object.getLibrary().getId());

            statement.setInt(5, object.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.DELETE_BOOK)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Book extractFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setNumberOfPages(resultSet.getInt("number_of_pages"));
        book.setLibrary(libraryDao.findById(resultSet.getInt("library_id")));
        return book;
    }
}
