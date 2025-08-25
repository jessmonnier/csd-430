<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.dm.Party" %>
<%@ page import="com.dm.Member" %>
<!--
Jess Monnier
CSD-430 Module 2 Assignment
This Page Created: 23 August 2025
Last Updated: 24 August 2025
A party tracker for D&D DMs using Java (JSP),
HTML, CSS, JavaScript, and MySQL.
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
        <h1>Module 3: Using Forms (a D&D DM party tracking application)</h1>

        <p>
            This is a project DM party tracker created as an assignment for my college Server Side Development course.
            It works with a MySQL database. To view the mySQL configuration necessary to work with this, please 
            <a href="https://github.com/jessmonnier/csd-380/blob/main/module-03/db_setup.sql" target="_blank">click here</a>.
        </p>
        <p>
            Required fields in the Add Party and Add Member forms have a dashed pink border if no valid data has been added.
        </p>

        <%
            // GET request to pull party info
            List<Party> parties = (List<Party>) request.getAttribute("parties");
            // check whether the get request returned info, and use it if so
            if (parties != null && !parties.isEmpty()) {
                // For loop to actually display the party information in a header
                // also includes a nested for loop to display party members in a table
                for (Party party : parties) {
                    // Format the day name nicely, title case, god I miss Python
                    String dayName = party.getMeetDay().toString().substring(0, 1).toUpperCase() +
                                     party.getMeetDay().toString().substring(1).toLowerCase();
                    // format start time as 24-hour time
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); 
                    String formattedTime = party.getStartTime().format(timeFormatter);
        %>
        <!-- Display party information for each party in a nice header -->
        <div class="party-header">
            <h2><%= party.getName() %></h2>
            <div class="party-info">meets <%= dayName %>s at <%= formattedTime %></div>
        </div>

        <!-- Set up the headers for the table of member information -->
        <table>
            <thead>
                <tr>
                    <th>Member Name</th>
                    <th>Phone Number</th>
                    <th>Character</th>
                    <th>Class</th>
                    <th>Race</th>
                    <th>STR</th>
                    <th>CON</th>
                    <th>DEX</th>
                    <th>INT</th>
                    <th>WIS</th>
                    <th>CHA</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Get the list of members stored within the party object
                    List<Member> members = party.getMembers();
                    // Check if it contains member info, and use it if so
                    if (members != null && !members.isEmpty()) {
                        for (Member member : members) {
                %>
                <!-- A row for each member, print out their info in each column -->
                <tr>
                    <td><%= member.getName() %></td>
                    <td><%= member.getPhoneNumber() %></td>
                    <td><%= member.getCharacterName() %></td>
                    <td><%= member.getCharacterClass() %></td>
                    <td><%= member.getCharacterRace() %></td>
                    <td><%= member.getStrength() %></td>
                    <td><%= member.getConstitution() %></td>
                    <td><%= member.getDexterity() %></td>
                    <td><%= member.getIntelligence() %></td>
                    <td><%= member.getWisdom() %></td>
                    <td><%= member.getCharisma() %></td>
                    <td>
                        <!-- The final column includes forms to move/delete each member -->
                        <form action="dm" method="post" style="display:inline;">
                            <input type="hidden" name="formType" value="moveMember">
                            <input type="hidden" name="memberId" value="<%= member.getId() %>">
                            <select name="newPartyName">
                                <% for (Party p : parties) { %>
                                    <!-- Option for each party, w shorthand condition to disable it for the current party -->
                                    <option value="<%= p.getName() %>" <%= p.getName().equals(party.getName()) ? "disabled" : "" %>>
                                        <%= p.getName() %>
                                    </option>
                                <% } %>
                            </select>
                            <button type="submit">Move</button>
                        </form>

                        <form action="dm" method="post" style="display:inline;">
                            <input type="hidden" name="formType" value="deleteMember">
                            <input type="hidden" name="memberId" value="<%= member.getId() %>">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <!-- This handles what displays if the party has no members in it -->
                <tr>
                    <td colspan="12">No members found for this party.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <!-- Add Member button -->
        <button class="form-toggle" onclick="toggleForm('member-form-<%= party.getName().hashCode() %>')">+ Add Member</button>

        <!-- Hidden Member form, toggles open/closed when Add Member button is pressed -->
        <form class="member-form hidden" id="member-form-<%= party.getName().hashCode() %>" action="dm" method="post">
            <input type="hidden" name="formType" value="addMember">
            <input type="hidden" name="partyName" value="<%= party.getName() %>">
            <input type="text" name="name" placeholder="Member Name" required>
            <input type="text" name="phoneNumber" placeholder="Phone Number">
            <input type="text" name="characterName" placeholder="Character Name" required>
            <input type="text" name="characterClass" placeholder="Class">
            <input type="text" name="characterRace" placeholder="Race">
            <!-- Add stat fields -->
            <input type="number" name="strength" placeholder="STR">
            <input type="number" name="constitution" placeholder="CON">
            <input type="number" name="dexterity" placeholder="DEX">
            <input type="number" name="intelligence" placeholder="INT">
            <input type="number" name="wisdom" placeholder="WIS">
            <input type="number" name="charisma" placeholder="CHA">
            <button type="submit">Save Member</button>
        </form>
        <%
                } // end for loop
            } else {
        %>
            <!-- Handle what displays when no parties are retrieved -->
            <p>No parties available to display.</p>
        <%
            }
        %>
        <br>
        <!-- Add Party button -->
        <button class="form-toggle" onclick="toggleForm('party-form')">+ Add Party</button>

        <!-- Hidden Add Party form, toggles open/closed when Add Party button is clicked. -->
        <form class="party-form hidden" id="party-form" action="dm" method="post">
            <input type="hidden" name="formType" value="addParty">
            <input type="text" name="name" placeholder="Party Name" required>
            <select name="meetDay" required>
                <option value="">Meeting Day</option>
                <option value="MONDAY">Monday</option>
                <option value="TUESDAY">Tuesday</option>
                <option value="WEDNESDAY">Wednesday</option>
                <option value="THURSDAY">Thursday</option>
                <option value="FRIDAY">Friday</option>
                <option value="SATURDAY">Saturday</option>
                <option value="SUNDAY">Sunday</option>
            </select>
            <!-- attempted to force 24-hour time w language but did not work for me, sadly -->
            <input type="time" name="startTime" required lang="en-GB">
            <button type="submit">Create Party</button>
        </form>
    </div>

    <div id="pad"></div>
    <footer>
        <div>
            ⓒ Jess Monnier 2025
        </div>
    </footer>
    <script>
        function toggleForm(id) {
            const form = document.getElementById(id);
            if (form.classList.contains('hidden')) {
                form.classList.remove('hidden');
            } else {
                form.classList.add('hidden');
            }
        }
    </script>
</body>
</html>