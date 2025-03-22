package com.tylerjohnson.eventcrafter.model;

import java.time.LocalDate;

public class Event {
    private int id;
    private String title;
    private String description;
    private String location;
    private LocalDate date;
    private String organizer;
    private int attendees;
    private String category; // This is a String, as ENUM isn't directly supported in Java
    

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
    
    // toString() for debugging
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
