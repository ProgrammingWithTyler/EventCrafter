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
import java.time.LocalDate;
import java.util.logging.*;

/**
 * Handles event creation and stores data in the database.
 *
 * @author Tyler
 */
@WebServlet("/create-event")
public class CreateEventServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateEventServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // Get session, but don't create if it doesn't exist

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        request.getRequestDispatcher("create-event.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");

        if (username == null || !isAuthorized(session)) {
            LOGGER.warning("Unauthorized access attempt to event creation");
            session.setAttribute("error", "Unauthorized access attempt to event creation.");
            response.sendRedirect(request.getContextPath() + "/create-event");
            return;
        }

        Event event = extractEventFromRequest(request);

        if (event == null) {
            request.setAttribute("error", "Missing or invalid event data.");
            request.setAttribute("events", EventDAO.getAllEvents());
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        // Authorization check: Only organizer level or high can create events.
        if (!event.getOrganizer().equals(username)) {
            LOGGER.warning("User does not have permission to delete this event.");
            request.getRequestDispatcher("create-event.jsp").forward(request, response);
        }

        event.setOrganizer(username);

        boolean success = EventDAO.addEvent(event);

        if (success) {
            request.setAttribute("message", "Event added successfully");
        } else {
            request.setAttribute("error", "Failed to add event");
        }

        request.setAttribute("events", EventDAO.getAllEvents());
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    private Event extractEventFromRequest(HttpServletRequest request) {
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String location = request.getParameter("location");
            String dateStr = request.getParameter("date"); // Format: YYYY-MM-DD
            String category = request.getParameter("category");
            int attendees = Integer.parseInt(request.getParameter("attendees"));

            if (title == null || title.isEmpty() || dateStr == null || dateStr.isEmpty() || category == null || category.isEmpty()) {
                return null;
            }

            LocalDate date = LocalDate.parse(dateStr);
            return new Event(0, title, description, location, date, "", attendees, category);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error passing event data: {0}", e.getMessage());
            return null; // If parsing fails, return null
        }
    }
    
    private boolean isAuthorized(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && (role.equals("organizer")  || role.equals("admin"));
    }

    @Override
    public String getServletInfo() {
        return "Handles event creation and stores data in the database.";
    }
}
