package com.tylerjohnson.eventcrafter.dao;

import static com.tylerjohnson.eventcrafter.db.DatabaseConnection.getConnection;
import com.tylerjohnson.eventcrafter.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 *
 * @author tyler
 */
public class UserEventDAO {
    
    private static final Logger LOGGER = Logger.getLogger(UserEventDAO.class.getName());
    
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
    
    public static UserEvent getUserEvent(String username, int eventId) {
        String query = "SELECT * FROM user_events ue JOIN users u ON ue.user_id = u.id WHERE u.username = ? AND ue.event_id = ?";
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
    
    public static List<Event> getRegisteredEvents(String username) {
        List<Event> registeredEvents = new ArrayList<>();
        String query = "SELECT e.* FROM events e JOIN user_events ue ON e.id = ue.event_id JOIN users u ON ue.user_id = u.id WHERE username = ?";
        
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
