package com.tylerjohnson.eventcrafter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Handles user authentication with secure password hashing.
 *
 * NOTE: This class is for academic purposes and has known security
 * vulnerabilities. It does not use proper password hashing or other security
 * best practices. DO NOT use this class in a production environment.
 */
public class DatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    // Get DataSource from the JNDI lookup;
    private static DataSource dataSource;

    // Initialize connection pool
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
            // Look up the DataSource from the JNDI context
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/eventcrafter");
            LOGGER.log(Level.INFO, "JNDI data source 'java:/comp/env/jdbc/eventcrafter' looked up successfully.");
        } catch (NamingException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error initializing data source: {0}", e.getMessage());
            throw new RuntimeException("Failed to intialize data source.", e);
        }
    }

    /**
     * Gets a database connection from the pool.
     *
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {

        if (dataSource == null) {
            throw new SQLException("DataSource not initialized properly");
        }
        return dataSource.getConnection();
    }

    /**
     * Validates user login with hashed passwords. WARNING: This method is
     * highly insecure and should not be used in production.
     *
     * @param username The username provided
     * @param password The raw password input by user
     * @return true if credentials are valid, false otherwise
     */
    public static boolean validateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            boolean result = rs.next();
            return result;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during user validation: {0}", e.getMessage());
        }
        return false;
    }

    // Check if a user exists in the database
    public static boolean userExists(String username) {
        String query = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // If a row exists, the user exists
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during user existence: {0}", e.getMessage());
        }
        return false;
    }

    // Register a new user
    public static boolean registerUser(String username, String password, String email) {

        if (userExists(username)) {
            return false; // User already exists
        }
        String query = "INSERT INTO users (username, password, email) VALUES (?,?,?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during user registration: {0}", e.getMessage());
        }
        return false;
    }

    public static List<String> getExistingTables() {
        List<String> tables = new ArrayList<>();
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'eventcrafter'";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during events table verification: {0}", e.getMessage());
        }
        return tables;
    }

    public static Map<String, Integer> getExistingTablesWithCount() {
        List<String> tables = new ArrayList<>();
        Map<String, Integer> result = new HashMap<>();
        int count = 0;
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'eventcrafter'";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                tables.add(tableName);
                count++;
            }
            result.put("count", count);
            result.put("tables", count);
            for (String tableName : tables) {
                result.put(tableName, 1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during events table verification: {0}", e.getMessage());
        }
        return result;
    }

    public static boolean verifySpecificTables(String... tableNames) {
        List<String> existingTables = getExistingTables();
        for (String tableName : tableNames) {
            if (!existingTables.contains(tableName)) {
                return false;
            }
        }
        return true;
    }
    
    public static String getUserRole(String username) {
        String role = null;
        String query = "SELECT role FROM eventcrafter.users WHERE username = ?";
        
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving user role: {0}", e.getMessage());
        }
        
        return role;
    }
}
