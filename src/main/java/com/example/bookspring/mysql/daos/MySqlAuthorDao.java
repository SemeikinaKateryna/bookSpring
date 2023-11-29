package com.example.bookspring.mysql.daos;

import com.example.bookspring.entity.Author;
import com.example.bookspring.mysql.DatabaseConnection;
import com.example.bookspring.mysql.queries.MySQLQuery;
import com.example.bookspring.mysql.queries.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlAuthorDao implements ResultSetExtractor<Author>{
    public List<Author> findByBookId(int bookId) {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.SELECT_ALL_BY_BOOK_ID)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = extractFromResultSet(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.FIND_BY_ID_AUTHOR)) {
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

    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.SELECT_ALL_AUTHOR)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = extractFromResultSet(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public boolean update(Author author) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.UPDATE_AUTHOR)) {
            statement.setString(1, author.getFullName());
            statement.setString(2, author.getCountry());

            statement.setInt(3, author.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAuthorFromBook (int bookId, int authorId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookAuthorRequestsSQL.DELETE_BY_BOOK_AUTHOR_ID)) {
            statement.setInt(1,bookId);
            statement.setInt(2, authorId);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addAuthorToBook(int bookId, int authorId){
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.BookAuthorRequestsSQL.INSERT_BOOK_AUTHOR)) {
            statement.setInt(1,bookId);
            statement.setInt(2, authorId);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Author object) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.INSERT_AUTHOR)) {
            statement.setString(1, object.getFullName());
            statement.setString(2, object.getCountry());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.DELETE_AUTHOR)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Author extractFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getInt("id"));
        author.setFullName(resultSet.getString("full_name"));
        author.setCountry(resultSet.getString("country"));
        return author;
    }



}
