package com.tylerjohnson.eventcrafter.servlets;

import com.tylerjohnson.eventcrafter.dao.*;
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
import java.util.List;

/**
 * Retrieves and displays key dashboard data, including event counts, user statistics, and other relevant information.
 * 
 * @author tyler
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DashboardServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Inside DashboardServlet.doGet method");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String username = (String) session.getAttribute("user");
        List<Event> registeredEvents = UserEventDAO.getRegisteredEvents(username);
        request.setAttribute("registeredEvents", registeredEvents);
        request.setAttribute("events", EventDAO.getAllEvents());
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Retrieves and displays key dashboard data, including event counts, user statistics, and other relevant information.";
    }

}
