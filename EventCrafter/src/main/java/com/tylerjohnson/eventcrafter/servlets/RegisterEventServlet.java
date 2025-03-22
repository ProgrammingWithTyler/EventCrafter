package com.tylerjohnson.eventcrafter.servlets;

import com.tylerjohnson.eventcrafter.dao.EventDAO;
import com.tylerjohnson.eventcrafter.dao.UserEventDAO;
import com.tylerjohnson.eventcrafter.model.UserEvent;
import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles user attendance for events.
 * 
 * @author tyler
 */
@WebServlet("/register-event")
public class RegisterEventServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterEventServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Inside AttendEventServlet.doPost method");
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("user");

        if (username == null) {
            LOGGER.warning("Unathorized access attempt to attend event.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        UserEvent userEvent = UserEventDAO.getUserEvent(username, eventId);

        if (userEvent != null) {
            request.setAttribute("error", "User already registered for this event.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }
        
        UserEvent newUserEvent = extractUserEventFromRequest(request, username);
        
        if (newUserEvent == null) {
            request.setAttribute("error", "Missing event ID.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        try {
            UserEventDAO.addUserEvent(newUserEvent);
            EventDAO.updateEventAttendence(eventId); // Update attendees count
            request.setAttribute("message", "Registration successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to record attendence: {0}", e.getMessage());
            request.setAttribute("error", "Failed to record attendence.");
        }

        request.setAttribute("events", EventDAO.getAllEvents());
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    private UserEvent extractUserEventFromRequest(HttpServletRequest request, String username) {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            UserEvent userEvent = new UserEvent();
            userEvent.setEventId(eventId);
            userEvent.setUsername(username);
            userEvent.setRegistrationDate(LocalDate.now());
            return userEvent;

        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid event ID format.");
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error extrating user event: {0}", e.getMessage());
            return null;
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles user attendance for events.";
    }
}
