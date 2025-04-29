package com.tylerjohnson.eventcrafter.model;

import java.time.LocalDate;

/**
 * Represents the relationship between a user and an event they've registered for.
 * 
 * This model is used to:
 * - Store and transfer data when a user registers for an event.
 * - Retrieve registration details (such as event ID, username, and registration date).
 * - Facilitate mapping between the user_events join table and application logic.
 * 
 * Typical usage: passed to/from DAO classes when interacting with the user_events table.
 * 
 * Fields:
 * - username: identifies the user by their username (not by their DB user_id).
 * - eventId: identifies the event the user registered for.
 * - registrationDate: when the user registered for the event.
 * 
 * This object abstracts away the database-specific details (like user_id) and makes the
 * application code cleaner and easier to test.
 * 
 * @author tyler
 */
public class UserEvent {
    
    // The username of the user who registered for the event
    private String username;
    
    // The ID of the event the user registered for.
    private int eventId;
    
    // The date on which the user registered for the event
    private LocalDate registrationDate;

    // No-args constructor for frameworks, tools or manual construction
    public UserEvent() {}
    
    // Full constructor for quick initialization
    public UserEvent(String username, int eventId, LocalDate registrationDate) {
        this.username = username;
        this.eventId = eventId;
        this.registrationDate = registrationDate;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    // toString() for debugging
    @Override
    public String toString() {
        return "UserEvent[" +
                "username=" + username +
                ", eventId='" + eventId + "\'" +
                ", registrationDate='" + registrationDate + "\'" +
                "]";
    }
}
