package com.example.bookspring.mysql;

public abstract class MySQLQuery {

    public static class BookRequestsSQL{
        public static final String FIND_BY_ID_BOOK = "SELECT * FROM Book WHERE book_id = ?";
        public static final String SELECT_ALL_BOOK = "SELECT * FROM Book";
        public static final String INSERT_BOOK = "INSERT INTO Book (title, author, " +
                "number_of_pages, library_id) VALUES (?, ?, ?, ?) ";
        public static final String UPDATE_BOOK= "UPDATE Book SET title = ?, author = ?, " +
                "number_of_pages = ?, library_id = ? WHERE book_id = ?";
        public static final String DELETE_BOOK = "DELETE FROM Book WHERE book_id = ?";
    }

    public static class LibraryRequestsSQL {
        public static final String FIND_BY_ID_LIBRARY = "SELECT * FROM Library WHERE library_id = ?";
    }
}
