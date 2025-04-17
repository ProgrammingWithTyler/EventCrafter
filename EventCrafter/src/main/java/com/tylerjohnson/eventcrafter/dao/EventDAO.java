package com.tylerjohnson.eventcrafter.dao;

import static com.tylerjohnson.eventcrafter.db.DatabaseConnection.getConnection;
import com.tylerjohnson.eventcrafter.model.Event;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventDAO {

    private static final Logger LOGGER = Logger.getLogger(EventDAO.class.getName());

    // Add an event to the database
    public static boolean addEvent(Event event) {
        LOGGER.log(Level.INFO, "Inside EventDAO.addEvent method");
        String query = "INSERT INTO eventcrafter.events (title, description, location, date,organizer, attendees, category) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setString(3, event.getLocation());
            pstmt.setDate(4, Date.valueOf(event.getDate()));
            pstmt.setString(5, event.getOrganizer());
            pstmt.setInt(6, event.getAttendees());
            pstmt.setString(7, event.getCategory());

            return pstmt.executeUpdate() > 0; // Returns true if insert was successful
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding event: {0}", e.getMessage());
        }
        return false;
    }

    // Get all events
    public static List<Event> getAllEvents() {
        LOGGER.log(Level.INFO, "Inside EventDAO.getAllEvents method");
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM eventcrafter.events ORDER BY date ASC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Event event = mapResultSetToEvent(rs);
                events.add(event);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all events: {0}", e.getMessage());
        }

        return events;
    }

    // Get event by id
    public static Event getEventById(int id) {
        String query = "SELECT * FROM eventcrafter.events WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return mapResultSetToEvent(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving event ID: {0}", e.getMessage());
        }

        return null;
    }

    // Get events created by a specific user
    public static List<Event> getEventsByUser(String username) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM eventcrafter.events WHERE organizer = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving events by user: {0}", e.getMessage());
        }
        return events;
    }

    // Delete an event by ID
    public static boolean deleteEvent(int eventId) {
        String query = "DELETE FROM eventcrafter.events WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, eventId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting event: {0}", e.getMessage());
        }
        return false;
    }

    // Update an event
    public static boolean updateEvent(Event event) {
        String query = "UPDATE eventcrafter.events SET title = ?, description = ?, location = ?, date = ?, attendees = ?, category = ? WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setString(3, event.getLocation());
            pstmt.setDate(4, Date.valueOf(event.getDate()));
            pstmt.setInt(5, event.getAttendees());
            pstmt.setString(6, event.getCategory());

            pstmt.setInt(7, event.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating event: {0}", e.getMessage());
            return false;
        }
    }

    // Pagination Methods
    public static List<Event> getEventsForPage(int page, int recordsPerPage) {
        LOGGER.log(Level.INFO, "Inside EventDAO.getEventsForPage method");
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM eventcrafter.events ORDER BY date ASC LIMIT ? OFFSET ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, recordsPerPage);
            pstmt.setInt(2, (page - 1) * recordsPerPage);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            LOGGER.log(Level.SEVERE, "Error retrieving events for page: {0}", e.getMessage());
        }

        return events;
    }

    public static int getTotalEvents() {
        LOGGER.log(Level.INFO, "Inside EventDAO.getTotalEvents method");
        String query = "SELECT COUNT(*) FROM eventcrafter.events";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting total events: {0}", e.getMessage());
        }

        return 0;
    }

    // Search/Filter Methods
    public static List<Event> searchEventByTitle(String title) {
        LOGGER.log(Level.INFO, "Inside EventDAO.searchEventByTitle method");
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM eventcrafter.events WHERE title LIKE ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching events by title: {0}", e.getMessage());
        }
        return events;
    }

    public static List<Event> filterEventsByCategory(String category) {
        LOGGER.log(Level.INFO, "Inside EventDAO.filterEventsByCategory method");
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM eventcrafter.events WHERE category = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            LOGGER.log(Level.SEVERE, "Error searching by category: {0}", e.getMessage());
        }
        return events;
    }

    public static List<Event> filterEventsByDateRange(LocalDate startDate, LocalDate endDate) {
        LOGGER.log(Level.INFO, "Inside EventDAO.filterEventsByDateRange method");
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM eventcrafter.events WHERE date BETWEEN ? AND ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error filtering events by date range: {0}", e.getMessage());
        }
        return events;
    }

    public static List<Event> searchAndFilterEvents(String title, String category, LocalDate startDate, LocalDate endDate) {
        LOGGER.log(Level.INFO, "Inside EventDAO.searchAndFilterEvents method");
        List<Event> events = new ArrayList<>();

        // Build dynamic query based on provided filters
        StringBuilder query = new StringBuilder("SELECT * FROM eventcrafter.events WHERE 1=1");

        if (title != null && !title.isEmpty()) {
            query.append(" AND title LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            query.append(" AND category = ?");
        }
        if (startDate != null && endDate != null) {
            query.append(" AND date BETWEEN ? AND ?");
        }

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Set parameters for title, if present
            if (title != null && !title.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + title + "%");
            }
            
            // Set parameters for category, if present
            if (category != null && !category.isEmpty()) {
                pstmt.setString(paramIndex++, category);
            }
            
            // Set parameters for date, if present
            if (startDate != null && endDate != null) {
                pstmt.setDate(paramIndex++ , Date.valueOf(startDate));
                pstmt.setDate(paramIndex++ , Date.valueOf(endDate));
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error filtering events: {0}", e.getMessage());
        }

        return events;
    }

    // Additional Get Methods
    public static List<Event> getUpcomingEvents() {
        LOGGER.log(Level.INFO, "Inside EventDAO.getUpcomingEvents method");
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM eventcrafter.events WHERE date >= CURDATE() ORDER BY date ASC";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving upcoming events: {0}", e.getMessage());
        }
        return events;
    }

    public static List<Event> getPastEvents() {
        LOGGER.log(Level.INFO, "Inside EventDAO.getPastEvents method");
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM eventcrafter.events WHERE date < CURDATE() ORDER BY date DESC";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving past events: {0}", e.getMessage());
        }
        return events;
    }

    public static void updateEventAttendence(int eventId) {
        String query = "UPDATE eventcrafter.events SET attendees = (SELECT COUNT(*) from user_events WHERE event_id = ?) WHERE id =?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, eventId);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update attendence: {0}", e.getMessage());
        }
    }

    // Helper method to map ResultSet to Event object
    private static Event mapResultSetToEvent(ResultSet rs) throws SQLException {
        return new Event(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getDate("date").toLocalDate(),
                rs.getString("organizer"),
                rs.getInt("attendees"),
                rs.getString("category")
        );
    }
}
