package com.tylerjohnson.eventcrafter.servlets;

import com.tylerjohnson.eventcrafter.dao.EventDAO;
import com.tylerjohnson.eventcrafter.model.Event;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Deletes an event from the database based on the provided event ID.
 * 
 * @author tyler
 */
@WebServlet("/delete-event")
public class DeleteEventServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteEventServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // Get session, but don't create if it doesn't exist
        String username = (String) session.getAttribute("user");

        if (username == null) {
            LOGGER.warning("Unauthorized access attempt to delete event.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String eventIdStr = request.getParameter("id");
        if (eventIdStr == null || eventIdStr.isEmpty()) {
            LOGGER.warning("Missing event ID for deletion");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);
            Event event = EventDAO.getEventById(eventId);
            
            if (event == null) {
                LOGGER.log(Level.WARNING, "Event not found: ID {0}", eventId);
                request.getSession().setAttribute("error", "Event not found.");
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
            
            // Authorization check: Only organizer level or high can delete events.
            if (!event.getOrganizer().equals(username)) {
                LOGGER.warning("User does not have permission to delete this event.");
                request.getSession().setAttribute("error", "You do not have permission to delete this event.");
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }

            boolean success = EventDAO.deleteEvent(eventId);
            if (success) {
                LOGGER.log(Level.INFO, "Event deleted successfully: ID {0}", eventId);
                request.getSession().setAttribute("messsage", "Event deleted successfully.");
            } else {
                LOGGER.log(Level.WARNING, "Failed to delete event from database: {0}", eventId);
                request.getSession().setAttribute("messsage", "Failed to delete event.");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid event ID format: {0}" + eventIdStr, e);
            request.getSession().setAttribute("error", "Invalid event ID.");
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    @Override
    public String getServletInfo() {
        return "Deletes an event from the database based on the provided event ID.";
    }

}
