package com.example.bookspring.mysql.daos;

import com.example.bookspring.entity.Book;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mysql.DatabaseConnection;
import com.example.bookspring.mysql.queries.MySQLQuery;
import com.example.bookspring.mysql.queries.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MySqlLibraryDao implements ResultSetExtractor<Library> {


    public Library findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.LibraryRequestsSQL.FIND_BY_ID_LIBRARY)) {
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
    public List<Library> findAll() {
        List<Library> libraries = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.LibraryRequestsSQL.SELECT_ALL_LIBRARY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Library library = extractFromResultSet(resultSet);
                libraries.add(library);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libraries;
    }
    @Override
    public Library extractFromResultSet(ResultSet resultSet) throws SQLException {
        Library library = new Library();
        library.setId(resultSet.getInt("id"));
        library.setAddress(resultSet.getString("address"));
        return library;
    }


}