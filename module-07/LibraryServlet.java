package com.library;

/* Jess Monnier
 * CSD-430 Module 7 Assignment
 * Created 6 September 2025
 * Modified 14 September 2025
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
                } else if (formType.startsWith("list")) {
                    String req;

                    // Figure out the query based on form submission
                    if (formType.equals("list_requests")) {
                        req = "TRUE";
                    } else { req = "FALSE"; }

                    String query = "SELECT * FROM jess_library_data WHERE requested = " + req;
                    String genre = request.getParameter("genre");
                    if (genre != null && !genre.isEmpty()) {
                        query += " AND (primary_genre = '" + genre + "' OR sub_genre = '" + genre + "')";
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
                                rs.getDate("out_date"),
                                rs.getBoolean("requested"),
                                rs.getDate("first_request"),
                                rs.getInt("times_requested")
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

    // Handle POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Determine the form type to decide what to do
        String formType = request.getParameter("formType");
        String[] selectedIds = request.getParameterValues("bookId");

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            if ("checkout_books".equals(formType) && selectedIds != null) {
                // Check out selected books
                String sql = "UPDATE jess_library_data SET checked_out = TRUE, out_date = CURDATE() WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    for (String id : selectedIds) {
                        ps.setInt(1, Integer.parseInt(id));
                        ps.executeUpdate();
                    }
                request.setAttribute("feedbackMsg", "The book(s) were successfully checked out.");
                }

            } else if ("vote_requests".equals(formType) && selectedIds != null) {
                // Increment request count
                String sql = "UPDATE jess_library_data SET times_requested = times_requested + 1 WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    for (String id : selectedIds) {
                        ps.setInt(1, Integer.parseInt(id));
                        ps.executeUpdate();
                    }
                request.setAttribute("feedbackMsg", "You successfully voted!");
                }

            } else if ("submit_request".equals(formType)) {
                String title = request.getParameter("title").trim();
                String author = request.getParameter("author").trim();
                String genre = request.getParameter("genre");
                String subgenre = request.getParameter("subgenre");

                // Check if book already exists (title + author match)
                String checkSQL = "SELECT COUNT(*) FROM jess_library_data WHERE title = ? AND author = ?";
                try (PreparedStatement checkStmt = con.prepareStatement(checkSQL)) {
                    checkStmt.setString(1, title);
                    checkStmt.setString(2, author);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        rs.next();
                        int count = rs.getInt(1);
                        if (count > 0) {
                            request.setAttribute("feedbackMsg", "That book is already in the database, so it was not added.");
                        } else {
                            // If it's not already in the library, add it
                            String insertSQL = "INSERT INTO jess_library_data (title, author, primary_genre, sub_genre, requested, first_request, times_requested) VALUES (?, ?, ?, ?, TRUE, CURDATE(), 1)";
                            try (PreparedStatement insertStmt = con.prepareStatement(insertSQL)) {
                                insertStmt.setString(1, title);
                                insertStmt.setString(2, author);
                                insertStmt.setString(3, genre);
                                insertStmt.setString(4, subgenre != null ? subgenre : null);
                                insertStmt.executeUpdate();
                                request.setAttribute("feedbackMsg", "Book request submitted successfully.");
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Database error during POST: " + e.getMessage(), e);
        }

        // Send the user back to the main page
        request.getRequestDispatcher("/library.jsp").forward(request, response);
    }
}
