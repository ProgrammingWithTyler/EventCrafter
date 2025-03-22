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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/events")
public class EventListServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EventListServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Inside EventListServlet.doGet method");
        HttpSession session = request.getSession(false); // Get session, but don't create if it doesn't exist

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
                LOGGER.log(Level.WARNING, "Invalid page parameter: {0}", request.getParameter("page"));
            }
        }

        List<Event> events = EventDAO.getEventsForPage(page, recordsPerPage);
        int totalEvents = EventDAO.getTotalEvents();
        int totalPages = (int) Math.ceil((double) totalEvents / recordsPerPage);

        request.setAttribute("events", events);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("event-list.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to display a list of events.";
    }

}
