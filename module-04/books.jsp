<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.books.Book" %>
<!--
Jess Monnier
CSD-430 Module 4 Assignment
This Page Created: 17 August 2025
Last Updated: 31 August 2025
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
        <h1>Module 4: Books I Enjoy</h1>
        <p>
            Module 2 but as a JavaBean! <a href="https://github.com/jessmonnier/csd-430/blob/main/module-04/db_setup.sql">This script</a>
            can be used to set up the MySQL database in the same way I did.
        </p>
            <!-- A table to hold the database results -->
            <table>
                <thead>
                    <tr>
                        <th>Cover</th>
                        <th>Information</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Use HTTP request to get the books from the servlet
                        List<Book> books = (List<Book>) request.getAttribute("books");
                        if (books != null && !books.isEmpty()) {
                            // loop to add each book to the table
                            for (Book book : books) {
                    %>
                        <tr>
                            <td><img src="module-02/images/<%= book.getCover() %>" alt="<%= book.getTitle() %> cover" width="150"></td>
                            <td>
                                <strong><%= book.getTitle() %></strong><br>
                                by <%= book.getAuthor() %><br>
                                Published in <%= book.getYear() %><br>
                                <p><%= book.getDescription() %></p>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <!-- Handle what happens when no books are found in database -->
                        <tr><td colspan="2">No books found.</td></tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
    </div>
    <div id="pad"></div>
    <footer>
        <div>
            ⓒ Jess Monnier 2025
        </div>
    </footer>
</body>

</html>