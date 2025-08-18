<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--
Jess Monnier
CSD-430 Module 1 Assignment
This Page Created: 17 August 2025
Last Updated: 17 August 2025
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
    <link rel="stylesheet" href="../style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Mulish:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
</head>

<body>
    <div id="header">
        <div id="title"><span>Playing with JSP</span></div>
    </div>
    <div id="container">
        <h1>Welcome to this test page for starting to learn JSP!</h1>
            <table>
                <thead>
                    <tr>
                        <% for (int j = 0; j < 5; j++) { %>
                            <th>Header</th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <% for (int i = 1; i < 5; i++) { %>
                        <tr>
                            <% for (int j = 0; j < 5; j++) { %>
                                <td>Data i = <%= i %> j = <%= j + 1 %></td>
                            <% } %>
                        </tr>
                    <% } %>
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