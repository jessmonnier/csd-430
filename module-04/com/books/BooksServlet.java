package com.books;
/* 
 * Jess Monnier
 * CSD-430 Module 4 Assignment
 * A program to display information about books from a MySQL database
 */

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import com.books.Book;

@WebServlet("/books") // set the url for the page
public class BooksServlet extends HttpServlet {

    // variables for the database info
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jsp";
    private static final String DB_USER = "student1";
    private static final String DB_PASS = "pass";

    // method to handle http request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // array to hold the database info
        List<Book> books = new ArrayList<>();

        // try blocks to handle the database connection/query
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[DEBUG] JDBC Driver loaded successfully.");

            try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            ) {
                System.out.println("[DEBUG] Connected to DB. Executing query...");

                // for logging purposes, count items found
                int rowCount = 0;
                // use query results to populate each book object
                while (rs.next()) {
                    Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("pubyear"),
                        rs.getString("cover"),
                        rs.getString("description")
                    );
                    books.add(book); // add book object to array

                    // logging
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

        // name the attribute for the http request
        request.setAttribute("books", books);
        System.out.println("[DEBUG] Set 'books' attribute with " + books.size() + " entries.");

        // forward visitor to correct page
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