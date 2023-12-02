package com.example.bookspring.mysql.queries;

public abstract class MySQLQuery {

    public static class BookRequestsSQL{
        public static final String FIND_BY_ID_BOOK = "SELECT * FROM Book WHERE id = ?";
        public static final String SELECT_ALL_BOOK = "SELECT * FROM Book";
        public static final String INSERT_BOOK = "INSERT INTO Book (library_id, title, pages, " +
                "year ) VALUES (?, ?, ?, ?) ";
        public static final String UPDATE_BOOK= "UPDATE Book SET library_id = ?, title = ?, pages = ?, " +
                "year = ?  WHERE id = ?";
        public static final String DELETE_BOOK = "DELETE FROM Book WHERE id = ?";
        public static final String FIND_BOOKS_BY_TITLE = "SELECT * FROM Book WHERE title LIKE ?";
        public static final String FIND_LAST = "SELECT * FROM Book ORDER BY id DESC LIMIT 1";
    }

    public static class LibraryRequestsSQL {
        public static final String FIND_BY_ID_LIBRARY = "SELECT * FROM Library WHERE id = ?";
        public static final String SELECT_ALL_LIBRARY = "SELECT * FROM Library";

        public static final String INSERT_LIBRARY = "INSERT INTO Library (address) VALUES (?)";
        public static final String DELETE_LIBRARY = "DELETE FROM Library WHERE id = ?";
    }

    public static class AuthorRequestsSQL {
        public static final String FIND_BY_ID_AUTHOR = "SELECT * FROM Author WHERE id = ?";
        public static final String SELECT_ALL_BY_BOOK_ID = "SELECT * FROM Author INNER JOIN Book_Author " +
                "ON author.id = book_author.author_id WHERE book_id = ?";
        public static final String SELECT_ALL_AUTHOR = "SELECT * FROM Author";
        public static final String UPDATE_AUTHOR = "UPDATE Author SET full_name = ?, country = ? " +
                "WHERE id = ?";
        public static final String INSERT_AUTHOR = "INSERT INTO Author (full_name, country) " +
                "VALUES (?, ?)";
        public static final String DELETE_AUTHOR = "DELETE FROM Author WHERE id = ?";

    }

    public static class BookAuthorRequestsSQL{
        public static final String INSERT_BOOK_AUTHOR = "INSERT INTO Book_Author (book_id, author_id) " +
                "VALUES (?, ?) ";
        public static final String DELETE_BY_BOOK_AUTHOR_ID = "DELETE FROM Book_Author WHERE book_id = ? AND author_id = ?";
    }
}
