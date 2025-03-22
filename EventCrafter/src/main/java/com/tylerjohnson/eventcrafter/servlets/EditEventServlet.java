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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles event editing and updates existing records in the database.
 *
 * @author tyler
 */
@WebServlet("/edit-event")
public class EditEventServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EditEventServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get session, but don't create if it doesn't exist

        if (session == null || session.getAttribute("user") == null) {
            LOGGER.warning("Unauthorized access attempt to event editing.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String eventIdStr = request.getParameter("id");
        if (eventIdStr == null || eventIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);
            Event event = EventDAO.getEventById(eventId);

            if (event == null) {
                LOGGER.log(Level.WARNING, "Event not found: ID {0}", eventId);
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }

            // Authorization check: Only organizer level or high can edit events.
            if (!event.getOrganizer().equals((String) session.getAttribute("user"))) {
                LOGGER.warning("User does not have permission to edit this event.");
                request.setAttribute("error", "You do not have permission to edit this event.");
                request.getRequestDispatcher("edit-event.jsp").forward(request, response);
                return;
            }

            request.setAttribute("event", event);
            request.getRequestDispatcher("edit-event.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid event ID format: " + eventIdStr, e);
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving event for editing: " + eventIdStr, e);
            request.setAttribute("error", "An error occurred while retrieving event for editing.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Get session, but don't create if it doesn't exist

        if (session == null || session.getAttribute("user") == null) {
            LOGGER.warning("Unauthorized access attempt to event editing.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Event event = extractEventFromRequest(request);

        if (event == null) {
            request.setAttribute("error", "Invalid event data");
            request.getRequestDispatcher("edit-event.jsp").forward(request, response);
        }

        // Authorization check: Only organizer level or high can edit events.
        if (!event.getOrganizer().equals((String) session.getAttribute("user"))) {
            LOGGER.warning("User does not have permission to edit this event.");
            request.setAttribute("error", "You do not have permission to edit this event.");
            request.getRequestDispatcher("edit-event.jsp").forward(request, response);
            return;
        }

        EventDAO.updateEvent(event);
        LOGGER.log(Level.INFO, "Event{0} updated by user: {1}", new Object[]{event.getId(), (String) session.getAttribute("user")});
        request.setAttribute("message", "Event updated successfully");
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    private Event extractEventFromRequest(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            int eventId = (id == null || id.isEmpty()) ? 0 : Integer.parseInt(id);

            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String location = request.getParameter("location");
            String dateStr = request.getParameter("date"); // Format: YYYY-MM-DD
            String category = request.getParameter("category");
            int attendees = Integer.parseInt(request.getParameter("attendees"));

            if (title == null || title.isEmpty() || dateStr == null || dateStr.isEmpty()) {
                return null;
            }

            LocalDate date = LocalDate.parse(dateStr);
            return new Event(eventId, title, description, location, date, "", attendees, category);
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
        return "Handles event editing and updates existing records in the database.";
    }

}
