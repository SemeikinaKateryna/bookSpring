package com.example.bookspring.mysql.queries;

public abstract class MySQLQuery {

    public static class BookRequestsSQL{
        public static final String FIND_BY_ID_BOOK = "SELECT * FROM Book WHERE id = ?";
        public static final String SELECT_ALL_BOOK = "SELECT * FROM Book";
        public static final String INSERT_BOOK = "INSERT INTO Book (library_id, title, pages, " +
                "year ) VALUES (?, ?, ?, ?, ?) ";
        public static final String UPDATE_BOOK= "UPDATE Book SET library_id = ?, title = ?, pages = ?, " +
                "year = ?  WHERE id = ?";
        public static final String DELETE_BOOK = "DELETE FROM Book WHERE id = ?";
        public static final String FIND_BOOKS_BY_TITLE = "SELECT * FROM Book WHERE title LIKE ?";
    }

    public static class LibraryRequestsSQL {
        public static final String FIND_BY_ID_LIBRARY = "SELECT * FROM Library WHERE id = ?";
        public static final String SELECT_ALL_LIBRARY = "SELECT * FROM Library";
    }

    public static class AuthorRequestsSQL {
        public static final String FIND_BY_ID_AUTHOR = "SELECT * FROM Author WHERE id = ?";

        public static final String SELECT_ALL_BY_BOOK_ID = "SELECT * FROM Author WHERE book_id = ?";
    }
}
