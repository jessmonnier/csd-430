package com.books;
/* 
 * Jess Monnier
 * CSD-430 Module 2 Assignment
 */

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import com.books.Book;

@WebServlet("/books")
public class BooksServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jsp";
    private static final String DB_USER = "movie_user";
    private static final String DB_PASS = "popcorn";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[DEBUG] JDBC Driver loaded successfully.");

            try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            ) {
                System.out.println("[DEBUG] Connected to DB. Executing query...");

                int rowCount = 0;
                while (rs.next()) {
                    Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("cover"),
                        rs.getString("description")
                    );
                    books.add(book);
                    System.out.println("[DEBUG] Book found: ID=" + book.getId() + ", Title=" + book.getTitle());
                    rowCount++;
                }

                System.out.println("[DEBUG] Total books found: " + rowCount);

            } catch (SQLException e) {
                System.err.println("[ERROR] SQL Exception:");
                e.printStackTrace();
                throw new ServletException("Database error: " + e.getMessage(), e);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] JDBC Driver class not found:");
            e.printStackTrace();
            throw new ServletException("JDBC Driver not found.", e);
        }

        request.setAttribute("books", books);
        System.out.println("[DEBUG] Set 'books' attribute with " + books.size() + " entries.");

        try {
            System.out.println("[DEBUG] Forwarding to JSP /module-02/books.jsp");
            request.getRequestDispatcher("/module-02/books.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("[ERROR] Exception while forwarding to JSP:");
            e.printStackTrace();
            throw new ServletException("Error forwarding to JSP: " + e.getMessage(), e);
        }
    }
}