package com.example.bookspring.mysql.daos;

import com.example.bookspring.dao.interfaces.IBookDao;
import com.example.bookspring.entity.Author;
import com.example.bookspring.entity.Book;
import com.example.bookspring.mysql.DatabaseConnection;
import com.example.bookspring.mysql.queries.MySQLQuery;
import com.example.bookspring.mysql.queries.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MySqlBookDao implements IBookDao, ResultSetExtractor<Book>{
    private final MySqlLibraryDao libraryDao = new MySqlLibraryDao();
    private final MySqlAuthorDao authorDao = new MySqlAuthorDao();

    @Override
    public Book findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
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
        try (Connection connection = DatabaseConnection.getConnection();
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
    public List<Book> findAllByTitle(String param) {
        String parameter = "%";
        String parameterParam = parameter.concat(param);
        String parameterFinal = parameterParam.concat("%");
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.FIND_BOOKS_BY_TITLE)) {
            statement.setString(1, parameterFinal);
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
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.INSERT_BOOK)) {
            statement.setInt(1, object.getLibrary().getId());
            statement.setString(2, object.getTitle());
            statement.setInt(3, object.getPages());
            statement.setInt(4, object.getYear());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Book object) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.UPDATE_BOOK)) {
            statement.setInt(1, object.getLibrary().getId());
            statement.setString(2, object.getTitle());
            statement.setInt(3, object.getPages());
            statement.setInt(4, object.getYear());

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
        try (Connection connection = DatabaseConnection.getConnection();
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
    public Book findLast() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookRequestsSQL.FIND_LAST)) {
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
    public Book extractFromResultSet(ResultSet resultSet) throws SQLException {
        List<Author> authors = authorDao.findByBookId(resultSet.getInt("id"));
        return new Book.Builder(resultSet.getInt("id"))
                .addTitle(resultSet.getString("title"))
                .addLibrary(libraryDao.findById(resultSet.getInt("library_id")))
                .addPages(resultSet.getInt("pages"))
                .addYear(resultSet.getInt("year"))
                .addAuthors(authors)
                .build();
    }
}
