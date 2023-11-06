package com.example.bookspring.mysql;

import com.example.bookspring.dao.IDao;
import com.example.bookspring.dao.MySqlBookDao;
import com.example.bookspring.entity.Book;
import com.example.bookspring.entity.Library;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LibraryDao implements IDao<Library>, ResultSetExtractor<Library>{
    @Override
    public Library findById(int id) {
        try (Connection connection = MySqlBookDao.getConnection();
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
        return null;
    }

    @Override
    public boolean insert(Library e) {
        return false;
    }

    @Override
    public boolean update(Library e) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Library extractFromResultSet(ResultSet resultSet) throws SQLException {
        Library library = new Library();
        library.setId(resultSet.getInt("library_id"));
        library.setAddress(resultSet.getString("address"));
        return library;
    }
}