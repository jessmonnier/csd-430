package com.bookstore;

/*
 * Jess Monnier
 * CSD-430 CRUD Project
 * Created 21 September 2025
 * Helper class to interact with MySQL database
 * Methods:
 * * getAllGenres()	        Fetches genres for dropdowns
 * * getBooks(String genre)	Returns all books or by genre
 * * addBook(Book book)	    Adds a new book
 * * updateBook(Book book)	Updates existing book by ID
 * * getBookById(int id)	Loads a book’s info for editing
 */

import java.sql.*;
import java.util.*;

public class BookstoreDB {

    // DB connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/csd430";
    private static final String DB_USER = "student1";
    private static final String DB_PASS = "pass";

    // Load JDBC Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get a connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // Fetch all genres from the database
    public static List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();

        String sql = "SELECT name FROM jess_bookstore_genres ORDER BY name";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                genres.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }

    // Fetch books (all or filtered by genre)
    public static List<Book> getBooks(String genreFilter) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM jess_bookstore_books";
        boolean hasFilter = genreFilter != null && !genreFilter.trim().isEmpty();

        if (hasFilter) {
            sql += " WHERE primary_genre = ? OR sub_genre = ?";
        }

        sql += " ORDER BY author, title";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (hasFilter) {
                stmt.setString(1, genreFilter);
                stmt.setString(2, genreFilter);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getString("primary_genre"),
                        rs.getString("sub_genre"),
                        rs.getInt("stock"),
                        rs.getBigDecimal("cost")
                    );
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Add a new book
    public static boolean addBook(Book book) {
        String sql = "INSERT INTO jess_bookstore_books (title, author, description, primary_genre, sub_genre, stock, cost) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getDescription());
            stmt.setString(4, book.getGenre());
            stmt.setString(5, book.getSubgenre());
            stmt.setInt(6, book.getStock());
            stmt.setBigDecimal(7, book.getCost());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an existing book
    public static boolean updateBook(Book book) {
        String sql = "UPDATE jess_bookstore_books SET title = ?, author = ?, description = ?, primary_genre = ?, sub_genre = ?, stock = ?, cost = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getDescription());
            stmt.setString(4, book.getGenre());
            stmt.setString(5, book.getSubgenre());
            stmt.setInt(6, book.getStock());
            stmt.setBigDecimal(7, book.getCost());
            stmt.setInt(8, book.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a single book by ID (used for editing)
    public static Book getBookById(int id) {
        String sql = "SELECT * FROM jess_bookstore_books WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getString("primary_genre"),
                        rs.getString("sub_genre"),
                        rs.getInt("stock"),
                        rs.getBigDecimal("cost")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}