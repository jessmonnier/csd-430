<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.library.Book" %>
<!--
Jess Monnier
CSD-430 Module 5 & 6 Assignment
This Page Created: 6 September 2025
Last Updated: 7 September 2025
-->
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
        <div id="title"><span>Playing with JSP</span></div>
    </div>
    <div id="container">
        <h1>Library Project</h1>
        <p>
            <a href="https://github.com/jessmonnier/csd-430/blob/main/module-06/db_setup.sql">This script</a>
            can be used to set up the MySQL database in the same way I did.
        </p>
            <!-- Book search form -->
            <form method="get" action="library">
                <input type="hidden" name="formType" value="list_books" />
                <label for="genre">Select Genre:</label>
                <select name="genre" id="genre">
                    <option value="" selected>Show All</option>
                    <%
                        List<String> genres = (List<String>) request.getAttribute("genres");
                        if (genres != null) {
                            for (String g : genres) {
                    %>
                        <option value="<%= g %>"><%= g %></option>
                    <%
                            }
                        }
                    %>
                </select>
                <button type="submit">Show Books</button>
            </form>

            <!-- Table of books -->
            <%
                List<Book> books = (List<Book>) request.getAttribute("books");
                if (books != null && !books.isEmpty()) {
            %>
                <h2>Books Found:</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Year</th>
                            <th>Genre</th>
                            <th>Subgenre</th>
                            <th>Description</th>
                            <th>Checked Out</th>
                            <th>Out Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Book book : books) {
                        %>
                            <tr class="<%= book.isCheckedOut() ? "checked-out" : "" %>">
                                <td><%= book.getTitle() %></td>
                                <td><%= book.getAuthor() %></td>
                                <td><%= book.getPubYear() %></td>
                                <td><%= book.getGenre() %></td>
                                <td><%= book.getSubgenre() %></td>
                                <td><%= book.getDescription() %></td>
                                <td><%= book.isCheckedOut() ? "Yes" : "No" %></td>
                                <td><%= book.getOutDate() != null ? book.getOutDate() : "N/A" %></td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            <%
                }
            %>
    </div>
    <div id="pad"></div>
    <footer>
        <div>
            ⓒ Jess Monnier 2025
        </div>
    </footer>
</body>

</html>