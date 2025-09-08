package com.library;

/* Jess Monnier
 * CSD-430 Module 5 & 6 Assignment
 * Created 6 September 2025
 * A program to serve as a hypothetical library's website
*/

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import com.library.Book;

@WebServlet("/library") // set the url for the page
public class LibraryServlet extends HttpServlet {

    // variables for the database info
    private static final String DB_URL = "jdbc:mysql://localhost:3306/csd430";
    private static final String DB_USER = "student1";
    private static final String DB_PASS = "pass";

    // method to handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // array to hold the database genres
        List<String> genres = new ArrayList<>();

        // try blocks to handle the database connection/query
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[DEBUG] JDBC Driver loaded successfully.");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                System.out.println("[DEBUG] Connected to DB. Executing query...");

                // load genres
                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM jess_library_genres")) {
                    while (rs.next()) {
                        genres.add(rs.getString("name"));
                    }
                    // logging
                    System.out.println("[DEBUG] genre list compiled");
                    request.setAttribute("genres", genres);
                }

                // load books, if requested
                String action = request.getServletPath(); // should always be /library
                String formType = request.getParameter("formType"); // passed from each form

                if (formType == null) {
                    request.getRequestDispatcher("/library.jsp").forward(request, response);
                    return;
                } else if (formType.equals("list_books")) {

                    // Figure out the query based on submission
                    String query = "SELECT * FROM jess_library_data";
                    String genre = request.getParameter("genre");
                    if (genre != null && !genre.isEmpty()) {
                        query += " WHERE primary_genre = '" + genre + "' OR sub_genre = '" + genre + "'";
                    }
                    query += " ORDER BY author";
                    try (Statement stmt = con.createStatement();
                         ResultSet rs = stmt.executeQuery(query)) {
                        
                        // Array to hold the books
                        List<Book> books = new ArrayList<>();

                        // Populate the array
                        while (rs.next()) {
                            Book book = new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getInt("pub_year"),
                                rs.getString("description"),
                                rs.getString("primary_genre"),
                                rs.getString("sub_genre"),
                                rs.getBoolean("checked_out"),
                                rs.getDate("out_date")
                            );
                        books.add(book);
                        }

                        // logging
                        System.out.println("[DEBUG] Books array successfully populated.");
                        request.setAttribute("books", books);
                    }
                }
            

            // catch blocks for the DB connection
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

        // forward visitor to correct page
        try {
            System.out.println("[DEBUG] Forwarding to JSP /library.jsp");
            request.getRequestDispatcher("/library.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("[ERROR] Exception while forwarding to JSP:");
            e.printStackTrace();
            throw new ServletException("Error forwarding to JSP: " + e.getMessage(), e);
        }
    }
}
