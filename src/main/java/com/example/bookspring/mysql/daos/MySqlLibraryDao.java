package com.example.bookspring.mysql.daos;

import com.example.bookspring.dao.interfaces.ILibraryDao;
import com.example.bookspring.entity.Author;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mysql.DatabaseConnection;
import com.example.bookspring.mysql.queries.MySQLQuery;
import com.example.bookspring.mysql.queries.ResultSetExtractor;
import com.example.bookspring.observer.Observer;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MySqlLibraryDao implements ILibraryDao, ResultSetExtractor<Library> {

    @Override
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

    @Override
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
        return new Library.Builder(resultSet.getInt("id"))
                        .addAddress(resultSet.getString("address"))
                        .build();
    }


    @Override
    public boolean insert(Library library) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (MySQLQuery.LibraryRequestsSQL.INSERT_LIBRARY)) {
            statement.setString(1, library.getAddress());
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
                     (MySQLQuery.LibraryRequestsSQL.DELETE_LIBRARY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}