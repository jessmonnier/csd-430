<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, java.math.BigDecimal" %>
<%@ page import="com.bookstore.Book" %>
<%@ page import="com.bookstore.BookstoreDB" %>
<%
    // === Variables for feedback and form mode ===
    String feedbackMsg = null;
    boolean isEditMode = false;
    Book editBook = null;

    // === Get parameters ===
    String action = request.getMethod(); // GET or POST
    String genreFilter = request.getParameter("genre");
    String editIdStr = request.getParameter("editId");
    String deleteIdStr = request.getParameter("deleteId");

    // POST handling: add or update book
    if ("POST".equalsIgnoreCase(action)) {
        // Retrieve form fields
        String formType = request.getParameter("formType"); // "add" or "edit"
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String description = request.getParameter("description");
        String primaryGenre = request.getParameter("genre");
        String subGenre = request.getParameter("subgenre");
        String stockStr = request.getParameter("stock");
        String costStr = request.getParameter("cost");

        int stock = 0;
        BigDecimal cost = BigDecimal.ZERO;
        try {
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            // Ignore or set default
            stock = 0;
        }
        try {
            cost = new BigDecimal(costStr);
        } catch (Exception e) {
            cost = BigDecimal.ZERO;
        }

        if ("edit".equalsIgnoreCase(formType)) {
            // Update book
            String idStr = request.getParameter("id");
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                id = 0;
            }

            if (id > 0) {
                Book bookToUpdate = new Book(id, title, author, description, primaryGenre, subGenre, stock, cost);
                boolean updated = BookstoreDB.updateBook(bookToUpdate);
                feedbackMsg = updated ? "Book updated successfully." : "Failed to update book.";
                genreFilter = primaryGenre; // show books in updated genre
            } else {
                feedbackMsg = "Invalid book ID for update.";
            }

        } else {
            // Add new book
            Book newBook = new Book(0, title, author, description, primaryGenre, subGenre, stock, cost);
            boolean added = BookstoreDB.addBook(newBook);
            feedbackMsg = added ? "Book added successfully." : "Failed to add book.";
            genreFilter = primaryGenre; // show books in added genre
        }
    }

    // If editId param is present, load the book to edit
    if (editIdStr != null && !editIdStr.trim().isEmpty()) {
        try {
            int editId = Integer.parseInt(editIdStr);
            editBook = BookstoreDB.getBookById(editId);
            if (editBook != null) {
                isEditMode = true;
                genreFilter = editBook.getGenre(); // show books in the genre of the book being edited
            }
        } catch (NumberFormatException e) {
            // ignore invalid editId
        }
    }

    // delete the book by ID...
    if (deleteIdStr != null && !deleteIdStr.trim().isEmpty()) {
        try {
            int deleteId = Integer.parseInt(deleteIdStr);
            boolean deleted = BookstoreDB.deleteBookById(deleteId);
            feedbackMsg = deleted ? "Book deleted successfully." : "Failed to delete book.";
        } catch (NumberFormatException e) {
            feedbackMsg = "Invalid book ID for deletion.";
        }
    }

    // Load genres for dropdown
    List<String> genres = BookstoreDB.getAllGenres();

    // Load books filtered by genre if set, else all
    List<Book> books = BookstoreDB.getBooks(genreFilter);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Testing out Server Side Development with JSP">
    <meta name="keywords" content="College course, HTML, CSS, Java, JSP">
    <meta name="author" content="Jess Monnier">
    <title>Playing with JSP - Home</title>
    <link rel="stylesheet" href="style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Mulish:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
</head>
<body>
    <div id="header">
        <h1>Bookstore Project</h1>
    </div>

    <div id="container">
        <p>
            <a href="https://github.com/jessmonnier/csd-430/blob/main/module-08/db_setup.sql" target="_blank">
                Database Setup Script
            </a>
        </p>

        <!-- Feedback message -->
        <% if (feedbackMsg != null) { %>
            <div class="feedback-message"><%= feedbackMsg %></div>
        <% } %>

        <!-- Filter by genre form -->
        <form method="get" action="bookstore.jsp" style="margin-bottom: 1em;">
            <label for="genreFilter">Filter by Genre:</label>
            <select name="genre" id="genreFilter" onchange="this.form.submit()">
                <option value="" <%= (genreFilter == null || genreFilter.isEmpty()) ? "selected" : "" %>>Show All</option>
                <% for (String g : genres) { %>
                    <option value="<%= g %>" <%= (g.equals(genreFilter)) ? "selected" : "" %>><%= g %></option>
                <% } %>
            </select>
            <noscript><button type="submit">Filter</button></noscript>
        </form>

        <!-- Books Table -->
        <% if (books != null && !books.isEmpty()) { %>
            <h2>Books <%= (genreFilter != null && !genreFilter.isEmpty()) ? ("in Genre: " + genreFilter) : "(All Genres)" %></h2>
            <table border="1" cellpadding="6" cellspacing="0">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Description</th>
                        <th>Primary Genre</th>
                        <th>Subgenre</th>
                        <th>Stock</th>
                        <th>Cost ($)</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <% for (Book book : books) { %>
                    <tr>
                        <td><%= book.getTitle() %></td>
                        <td><%= book.getAuthor() %></td>
                        <td><%= (book.getDescription() != null && !book.getDescription().trim().isEmpty()) ? book.getDescription() : "TBD" %></td>
                        <td><%= book.getGenre() %></td>
                        <td><%= book.getSubgenre() != null ? book.getSubgenre() : "" %></td>
                        <td><%= book.getStock() %></td>
                        <td><%= book.getCost() %></td>
                        <td>
                            <div style="display:flex; gap:0.5em; align-items:center;">
                                <form method="get" action="bookstore.jsp" style="margin:0;">
                                    <input type="hidden" name="editId" value="<%= book.getId() %>" />
                                    <button type="submit" title="Edit" style="background:none; border:none; cursor:pointer; font-size:1.2em;">✏️</button>
                                </form>
                                <form method="get" action="bookstore.jsp" style="margin:0;" onsubmit="return confirm('Are you sure you want to delete this book?');">
                                    <input type="hidden" name="deleteId" value="<%= book.getId() %>" />
                                    <button type="submit" title="Delete" style="background:none; border:none; cursor:pointer; font-size:1.2em;">🗑️</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No books found.</p>
        <% } %>

        <!-- Add/Edit Book Form -->
        <h2><%= isEditMode ? "Edit Book" : "Add New Book" %></h2>
        <form method="post" action="bookstore.jsp">
            <input type="hidden" name="formType" value="<%= editBook != null ? "edit" : "add" %>">
            <% if (editBook != null) { %>
                <input type="hidden" name="id" value="<%= editBook.getId() %>">
            <% } %>
            <table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Description</th>
                        <th>Primary Genre</th>
                        <th>Subgenre</th>
                        <th>Stock</th>
                        <th>Cost</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="text" name="title" required value="<%= editBook != null ? editBook.getTitle() : "" %>"></td>
                        <td><input type="text" name="author" required value="<%= editBook != null ? editBook.getAuthor() : "" %>"></td>
                        <td><textarea name="description" rows="3" cols="50"><%= isEditMode ? (editBook.getDescription() != null ? editBook.getDescription() : "") : "" %></textarea></td>
                        <td>
                            <select name="genre" required style="width:120px;">
                                <option value="" disabled <%= (editBook == null) ? "selected" : "" %>>Select Genre</option>
                                <% for (String g : genres) { %>
                                    <option value="<%= g %>" <%= (editBook != null && g.equals(editBook.getGenre())) ? "selected" : "" %>><%= g %></option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            <select name="subgenre" style="width:120px;">
                                <option value="" <%= (editBook == null || editBook.getSubgenre() == null) ? "selected" : "" %>>Select Subgenre</option>
                                <% for (String g : genres) { %>
                                    <option value="<%= g %>" <%= (editBook != null && g.equals(editBook.getSubgenre())) ? "selected" : "" %>><%= g %></option>
                                <% } %>
                            </select>
                        </td>
                        <td><input type="number" name="stock" min="0" required value="<%= editBook != null ? editBook.getStock() : "0" %>" style="width: 50px;"></td>
                        <td><input type="number" name="cost" step="0.01" min="0" required value="<%= editBook != null ? editBook.getCost() : "0.00" %>" style="width: 60px;"></td>
                        <td>
                            <button type="submit"><%= editBook != null ? "Update" : "Add" %></button>
                            <% if (isEditMode) { %>
                            &nbsp; <a href="bookstore.jsp">Cancel</a>
                            <% } %>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
    <div id="pad"></div>
    <footer>
        <div>© Jess Monnier 2025</div>
    </footer>
</body>
</html>