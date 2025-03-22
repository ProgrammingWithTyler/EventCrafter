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
 * Displays details of a specific event.
 * 
 * @author tyler
 */
@WebServlet("/event-details")
public class EventDetailsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EventDetailsServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // Get session, but don't create if doesn't exist
        
        if (session == null || session.getAttribute("user") == null) {
            // if the user is not logged in, redirect them.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));
            Event event = EventDAO.getEventById(eventId);
            
            if (event != null) {
                request.setAttribute("event", event);
                request.getRequestDispatcher("event-details.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Event not found");
                request.getRequestDispatcher("event-details.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid event ID: {0}", request.getParameter("id"));
            request.setAttribute("error", "An error occurred while retrieving event details.");
            response.sendRedirect(request.getContextPath() + "/events");
        }
    }

    @Override
    public String getServletInfo() {
        return "Displays details of a specific event.";
    }

}
