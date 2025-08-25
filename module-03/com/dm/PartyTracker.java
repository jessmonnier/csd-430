package com.dm;
/* 
 * Jess Monnier
 * CSD-430 Module 2 Assignment
 * A servlet to handle the logic of adding parties & party members
 * in a D&D party tracking JSP page paired with MySQL
 */

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import com.dm.Party;
import com.dm.Member;

@WebServlet("/dm") // Control the URL where the servlet is hosted
public class PartyTracker extends HttpServlet {

    // MySQL connection variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jsp";
    private static final String DB_USER = "student1";
    private static final String DB_PASS = "pass";

    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Info from the DB "party" table will be stored in a Party type ArrayList
        List<Party> parties = new ArrayList<>();

        try {
            // try block to handle potential for ClassNotFoundException
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[DEBUG] JDBC Driver loaded successfully.");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm[:ss]");

            // try block (with resources) to handle the actual MySQL connection
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                System.out.println("[DEBUG] Connected to DB.");

                // Load all parties; try-with-resources to handle the actual query & potential errors
                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM party")) {

                    int partyCount = 0; // for logging purposes
                    while (rs.next()) {
                        // Use the result set to populate the Party constructor
                        // Some tweaking required to meet the expected data type for each Party variable
                        Party party = new Party(
                            rs.getString("name"),
                            DayOfWeek.valueOf(rs.getString("meet_day").toUpperCase()),
                            LocalTime.parse(rs.getString("start_time"), formatter)
                        );
                        parties.add(party); // add the new Party object to the ArrayList
                        
                        // logging stuff
                        System.out.println("[DEBUG] Party found: NAME=" + party.getName() + ".");
                        partyCount++;
                    }
                    System.out.println("[DEBUG] Total parties found: " + partyCount);
                }

                // Build a map for quick party lookup by name (minimizes number of queries sent to database)
                Map<String, Party> partyMap = new HashMap<>();
                for (Party p : parties) {
                    partyMap.put(p.getName(), p);
                }

                // Bulk load all members
                String memberQuery = "SELECT * FROM member";
                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery(memberQuery)) {

                    int memberCount = 0; // for logging purposes
                    while (rs.next()) {
                        // use the foreign key party_name and the partyMap to ensure 
                        // member is added to proper party; possible bc of pass-by-value of reference
                        String partyName = rs.getString("party_name");
                        Party party = partyMap.get(partyName);

                        // Make a Member object for the current member
                        if (party != null) {
                            Member member = new Member(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("character_name"),
                                rs.getString("character_class"),
                                rs.getString("character_race"),
                                rs.getString("phone_number"),
                                rs.getInt("strength"),
                                rs.getInt("intelligence"),
                                rs.getInt("charisma"),
                                rs.getInt("constitution"),
                                rs.getInt("dexterity"),
                                rs.getInt("wisdom")
                            );
                            // Add the Member object to the appropriate party
                            party.addMember(member);
                            
                            // logging stuff; not these messages should be overly cautious since
                            // the party_name fk is set to cascade if party is deleted, but better to be safe
                            memberCount++;
                            System.out.println("[DEBUG] Member found: NAME=" + member.getName() + " in party " + partyName + ".");
                        } else {
                            System.out.println("[WARN] Member found for unknown party: " + partyName);
                        }
                    }
                    System.out.println("[DEBUG] Total members found: " + memberCount);
                }
            // Stepping out with the various catch statements the accumulated try blocks necessitated
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

        // name the attribute so we can perform the GET request from JSP page
        request.setAttribute("parties", parties);
        // more logging
        System.out.println("[DEBUG] Set 'parties' attribute with " + parties.size() + " entries.");

        // handle the requests/ensuring user is on the right page at the end etc
        try {
            System.out.println("[DEBUG] Forwarding to JSP /module-03/partytracker.jsp");
            request.getRequestDispatcher("/module-03/partytracker.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("[ERROR] Exception while forwarding to JSP:");
            e.printStackTrace();
            throw new ServletException("Error forwarding to JSP: " + e.getMessage(), e);
        }
    }

    // handle the POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath(); // in this case should be "/dm" (default for all these forms)

        String formType = request.getParameter("formType"); // This is passed from each form
        if (formType == null) {
            response.sendRedirect("dm"); // fallback
            return;
        }

        // try-with-resources to connect to the database
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // switch to catch the formType values & pass to correct helper method
            switch (formType) {
                case "addParty":
                    handleAddParty(request, con);
                    break;

                case "addMember":
                    handleAddMember(request, con);
                    break;

                case "moveMember":
                    handleMoveMember(request, con);
                    break;

                case "deleteMember":
                    handleDeleteMember(request, con);
                    break;

                default:
                    System.err.println("[WARN] Unknown formType: " + formType);
                    break;
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] SQL error in doPost:");
            e.printStackTrace();
        }

        // After processing, redirect to reload data
        response.sendRedirect("dm");
    }

    // method to add a new party based on form info
    private void handleAddParty(HttpServletRequest request, Connection con) throws SQLException {
        // add the form data to appropriate variables, formatted where appropriate
        String name = request.getParameter("name");
        String meetDay = request.getParameter("meetDay").toUpperCase();
        String startTime = request.getParameter("startTime");

        String sql = "INSERT INTO party (name, meet_day, start_time) VALUES (?, ?, ?)";

        // submit the MySQL query with the form data
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, meetDay);
            stmt.setString(3, startTime);
            stmt.executeUpdate();
            System.out.println("[DEBUG] Added party: " + name);
        }
    }

    // method to add a new member to a party
    private void handleAddMember(HttpServletRequest request, Connection con) throws SQLException {
        // add the form data to appropriate variables, formatted where appropriate
        String partyName = request.getParameter("partyName");
        String name = request.getParameter("name");
        String characterName = request.getParameter("characterName");
        String characterClass = request.getParameter("characterClass");
        String characterRace = request.getParameter("characterRace");
        String phoneNumber = request.getParameter("phoneNumber");

        int str = Integer.parseInt(request.getParameter("strength"));
        int conVal = Integer.parseInt(request.getParameter("constitution"));
        int dex = Integer.parseInt(request.getParameter("dexterity"));
        int intel = Integer.parseInt(request.getParameter("intelligence"));
        int wis = Integer.parseInt(request.getParameter("wisdom"));
        int cha = Integer.parseInt(request.getParameter("charisma"));

        String sql = "INSERT INTO member (party_name, name, character_name, character_class, character_race, phone_number, strength, constitution, dexterity, intelligence, wisdom, charisma) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // submit the MySQL query with the form data
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, partyName);
            stmt.setString(2, name);
            stmt.setString(3, characterName);
            stmt.setString(4, characterClass);
            stmt.setString(5, characterRace);
            stmt.setString(6, phoneNumber);
            stmt.setInt(7, str);
            stmt.setInt(8, conVal);
            stmt.setInt(9, dex);
            stmt.setInt(10, intel);
            stmt.setInt(11, wis);
            stmt.setInt(12, cha);
            stmt.executeUpdate();
            System.out.println("[DEBUG] Added member: " + name + " to " + partyName);
        }
    }

    // method to handle moving a member to a different party
    private void handleMoveMember(HttpServletRequest request, Connection con) throws SQLException {
        // add the form data to appropriate variables, formatted where appropriate
        int memberId = Integer.parseInt(request.getParameter("memberId"));
        String newParty = request.getParameter("newPartyName");

        String sql = "UPDATE member SET party_name = ? WHERE id = ?";

        // submit the MySQL query with the form data
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, newParty);
            stmt.setInt(2, memberId);
            stmt.executeUpdate();
            System.out.println("[DEBUG] Moved member ID " + memberId + " to " + newParty);
        }
    }

    // method to handle a request to delete a member from a party
    private void handleDeleteMember(HttpServletRequest request, Connection con) throws SQLException {
        // add the form data to appropriate variables, formatted where appropriate
        int memberId = Integer.parseInt(request.getParameter("memberId"));

        String sql = "DELETE FROM member WHERE id = ?";

        // submit the MySQL query with the form data
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.executeUpdate();
            System.out.println("[DEBUG] Deleted member ID " + memberId);
        }
    }

}