package com.example.bookspring.mysql.daos;

import com.example.bookspring.dao.interfaces.IAuthorDao;
import com.example.bookspring.entity.Author;
import com.example.bookspring.mysql.DatabaseConnection;
import com.example.bookspring.mysql.queries.MySQLQuery;
import com.example.bookspring.mysql.resultextractor.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.bookspring.observer.*;

public class MySqlAuthorDao implements IAuthorDao, ResultSetExtractor<Author> {
    private Author author;
    private final List<Observer<Author>> observers = new ArrayList<>();

    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    public void notifyObserversUpdate() {
        for (Observer<Author> observer : this.observers) {
            observer.update(author);
        }
    }

    public void notifyObserversInsert() {
        for (Observer<Author> observer : this.observers) {
            observer.insert(author);
        }
    }

    public void notifyObserversDelete() {
        for (Observer<Author> observer : this.observers) {
            observer.delete(author);
        }
    }

    @Override
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
    @Override
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

    @Override
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
    @Override
    public boolean update(Author author) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.UPDATE_AUTHOR)) {
            statement.setString(1, author.getFullName());
            statement.setString(2, author.getCountry());

            statement.setInt(3, author.getId());
            statement.executeUpdate();

            this.author = author;
            notifyObserversUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
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
    @Override
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
    @Override
    public boolean insert(Author object) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.INSERT_AUTHOR)) {
            statement.setString(1, object.getFullName());
            statement.setString(2, object.getCountry());
            statement.executeUpdate();

            this.author = object;

            notifyObserversInsert();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean delete(int id) {
        this.author = findById(id);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.AuthorRequestsSQL.DELETE_AUTHOR)) {
            statement.setInt(1, id);
            statement.executeUpdate();

            notifyObserversDelete();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Author extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Author.Builder(resultSet.getInt("id"))
                .addFullName(resultSet.getString("full_name"))
                .addCountry(resultSet.getString("country"))
                .build();

    }
}
