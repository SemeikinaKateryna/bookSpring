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

public class MySqlAuthorDao implements ResultSetExtractor<Author> {
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
    public Author extractFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getInt("id"));
        author.setFullName(resultSet.getString("full_name"));
        author.setCountry(resultSet.getString("country"));
        return author;
    }


}
