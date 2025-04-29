package com.tylerjohnson.eventcrafter.dao;

import static com.tylerjohnson.eventcrafter.db.DatabaseConnection.getConnection;
import com.tylerjohnson.eventcrafter.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 * DAO (Data Access Object) for managing user-event relationships.
 * This class handles registering user for events, fetching registration details,
 * and listing all events a specific user has signed up for.
 * 
 * Database Tables Involved:
 * - users
 * - events
 * - user_events (join table)
 * 
 * @author tyler
 */
public class UserEventDAO {
    
    private static final Logger LOGGER = Logger.getLogger(UserEventDAO.class.getName());
    
    /**
     * Registers a user for an event by inserting a record into the user_events table.
     * It first looks up the user's ID by their username.
     * If the user exists, it inserts the user-event relationship with a registration date.
     * This is a transactional operation with rollback on failure.
     * 
     * @param userEvent The UserEvent object containing username, eventId, and registrationDate.
     * @param userEvent 
     */
    public static void addUserEvent(UserEvent userEvent) {
        LOGGER.log(Level.INFO, "Inside UserEventDAO.addUserEvent method");
        String userIdQuery = "SELECT id FROM eventcrafter.users WHERE username = ?";
        String insertQuery = "INSERT INTO eventcrafter.user_events (user_id, event_id, registration_date) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement userIdStmt = conn.prepareStatement(userIdQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            
            conn.setAutoCommit(false); // Begin transaction
            
            //Get user_id from username
            userIdStmt.setString(1, userEvent.getUsername());
            ResultSet rs = userIdStmt.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("id");
                
                // Insert into user_events
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, userEvent.getEventId());
                insertStmt.setDate(3, Date.valueOf(userEvent.getRegistrationDate()));
                
                insertStmt.executeUpdate();
                
                conn.commit(); // Commit transaction
            } else {
                LOGGER.log(Level.WARNING, "User not found: {0}", userEvent.getUsername());
                conn.rollback(); // Rollback transaction
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error: {0}", e.getMessage());
            e.printStackTrace();
            
            try (Connection conn = getConnection()) {
                conn.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Database error during rollback: {0}", ex.getMessage());
            }
        }
    }
    
    /**
     * Retrieves the UserEvent record for a specific user and event combination.
     * Useful for checking if a user is already registered for an event.
     * 
     * @param username The username of the user.
     * @param eventId The ID of the event.
     * @return A UserEvent object if the user is registered, null otherwise.
     * @param username
     * @param eventId
     * @return 
     */
    public static UserEvent getUserEvent(String username, int eventId) {
        String query = "SELECT * FROM eventcrafter.user_events ue JOIN users u ON ue.user_id = u.id WHERE u.username = ? AND ue.event_id = ?";
        UserEvent userEvent = null;
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, eventId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                userEvent = new UserEvent();
                userEvent.setEventId(rs.getInt("event_id"));
                userEvent.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                userEvent.setUsername(username);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error: {0}", e.getMessage());
        }
        
        return userEvent;
    }
    
    /**
     * Retrieves a list of all events a user has registered for.
     * Uses a JOIN between users, events, and user_events to return full event details.
     * 
     * @param username The username of the user whose events are to be fetched.
     * @return A list of event objects the user is registered for.
     */
    public static List<Event> getRegisteredEvents(String username) {
        List<Event> registeredEvents = new ArrayList<>();
        String query = "SELECT e.* FROM eventcrafter.events e JOIN user_events ue ON e.id = ue.event_id JOIN users u ON ue.user_id = u.id WHERE username = ?";
        
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setLocation(rs.getString("location"));
                event.setDate(rs.getDate("date").toLocalDate());
                event.setOrganizer(rs.getString("organizer"));
                event.setAttendees(rs.getInt("attendees"));
                event.setCategory(rs.getString("category"));
                registeredEvents.add(event);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error: {0}", e.getMessage());
        }
        return registeredEvents;
    }
}
