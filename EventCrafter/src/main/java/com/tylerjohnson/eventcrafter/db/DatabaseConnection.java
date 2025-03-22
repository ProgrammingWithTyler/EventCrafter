package com.tylerjohnson.eventcrafter.db;

import com.tylerjohnson.eventcrafter.servlets.CreateEventServlet;
import java.sql.Connection;
import java.sql.DriverManager;
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

/**
 * Handles user authentication with secure password hashing.
 * 
 * NOTE: This class is for academic purposes and has known security vulnerabilities.
 * It does not use proper password hashing or other security best practices.
 * DO NOT use this class in a production environment.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/eventcrafter?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    
    // Initialize connection pool
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found: {0}", e.getMessage());
        }
    }
    
    /**
     * Gets a database connection from the pool.
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * Validates user login with hashed passwords.
     * WARNING: This method is highly insecure and should not be used in production.
     * @param username The username provided
     * @param password The raw password input by user
     * @return true if credentials are valid, false otherwise 
     */
    public static boolean validateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during user validation: {0}", e.getMessage());
        }
        return false;
    }
    
    // Check if a user exists in the database
    public static boolean userExists(String username) {
        String query = "SELECT 1 FROM users WHERE username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
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
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
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
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
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
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
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
        for(String tableName : tableNames) {
            if (!existingTables.contains(tableName)) {
                return false;
            }
        }
        return true;
    }
    
    // Verifies the connection is successfully connected.
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            
            Map<String, Integer> tableInfo = DatabaseConnection.getExistingTablesWithCount();
            int tableCount = tableInfo.get("count");
            List<String> tables = new ArrayList<>(tableInfo.keySet());
            tables.remove("count");
            tables.remove("tables");
            System.out.println("Table count: " + tableCount);
            System.out.println("Tables: " + tables);
            if (conn != null) {
                System.out.println("Database connection successful!");
            } else {
                System.out.println("Failed to connect to database");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection test failed: {0}", e.getMessage());
        }
    }
            
}
