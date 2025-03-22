package com.tylerjohnson.eventcrafter.model;

import java.time.LocalDate;

/**
 *
 * @author tyler
 */
public class UserEvent {
    private String username;
    private int eventId;
    private LocalDate registrationDate;

    public UserEvent() {
    }
    
    

    public UserEvent(String username, int eventId, LocalDate registrationDate) {
        this.username = username;
        this.eventId = eventId;
        this.registrationDate = registrationDate;
    }

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
}
