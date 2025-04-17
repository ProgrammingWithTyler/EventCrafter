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
        String username = (session != null) ? (String) session.getAttribute("user") : null;

        if (username == null || !isAuthorized(session)) {
            LOGGER.warning("Unauthorized event creation attempt");
            if (session != null) {
                session.setAttribute("error", "Access denied.");
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Event event = extractEventFromRequest(request);

        if (event == null) {
            request.setAttribute("error", "Missing or invalid event data.");
            request.setAttribute("events", EventDAO.getAllEvents());
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        event.setOrganizer(username);

        boolean success = EventDAO.addEvent(event);
        request.setAttribute(success ? "message" : "error", success ? "Event added successfully." : "Failed to add event.");

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

            LOGGER.log(Level.INFO, "Event data: title={0}, date={1}, category={2}", new Object[]{title, dateStr, category});

            if (title == null || title.isEmpty()) {
                LOGGER.warning("Title is null or empty");
                return null;
            }

            if (dateStr == null || dateStr.isEmpty()) {
                LOGGER.warning("Date string is null or empty");
                return null;
            }

            if (category == null || category.isEmpty()) {
                LOGGER.warning("Category is null or empty");
                return null;
            }
            
            int attendees;
            String attendeesStr = request.getParameter("attendees");
            try {
                attendees = Integer.parseInt(attendeesStr);
            } catch (NumberFormatException e) {
                LOGGER.warning("");
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
        LOGGER.info("role: " + role);
        return role != null && (role.equals("organizer") || role.equals("admin"));
    }

    @Override
    public String getServletInfo() {
        return "Handles event creation and stores data in the database.";
    }
}
