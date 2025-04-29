package com.tylerjohnson.eventcrafter.model;

import java.time.LocalDate;

/**
 * Represents an event in the system, containing all metadata necessary for display, organization, and user registration.
 * 
 * This model maps directly to the "events" table in the database.
 * It is used throughout the app for:
 * - creating new events
 * - Displaying event details
 * - Querying events a user has registered for
 * 
 * Fields:
 * - id: unique identifier for the event (used as primary key)
 * - title: name of the event
 * - description: detailed information about the event
 * - location: where the event is held
 * - date: when the event takes place
 * - organizer: who is hosting the event
 * - attendees: how many people are expected or currently registered
 * - category: classification of the event (e.g., conference, workshop, etc.)
 *   (Stored as a String; enums aren't directly persisted in JDBC without conversion)
 * 
 * Common usage:
 * This class is returned by DAOs and passed to views and controllers.
 * 
 * @author Tyler
 * 
 */
public class Event {
    
    // Unique identifier of the event (primary key)
    private int id;
    
    // Title of the event
    private String title;
    
    // Description of the event
    private String description;
    
    // Location where the event is being held
    private String location;
    
    // Date of the event
    private LocalDate date;
    
    // Name of the organizer
    private String organizer;
    
    // Number of attendees expected or registered
    private int attendees;
    
    // Category of the event (This is a String, as ENUM isn't directly supported in Java)
    private String category;
    

    // Constructor with no fields
    public Event() {}
    
    // Constructor for new events (without ID)
    public Event(String title, String description, String location, LocalDate date, String organizer, int attendees, String category) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.organizer = organizer;
        this.attendees = attendees;
        this.category = category;
    }

    // Constructor for new events (with ID)
    public Event(int id, String title, String description, String location, LocalDate date, String organizer, int attendees, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.organizer = organizer;
        this.attendees = attendees;
        this.category = category;
    }
    
    // Getters & Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getOrganizer() {
        return organizer;
    }
    
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
    
    public int getAttendees() {
        return attendees;
    }
    
    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    // toString() for debugging/logging purposes
    @Override
    public String toString() {
        return "Event[" +
                "id=" + id +
                ", title='" + title + "\'" +
                ", description='" + description + "\'" +
                ", date='" + date + "\'" +
                ", organizer='" + organizer + "\'" +
                ", attendees='" + attendees + "\'" +
                ", category='" + category + "\'" +
                "]";
    }

}
