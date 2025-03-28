package com.tylerjohnson.eventcrafter.servlets;

import com.tylerjohnson.eventcrafter.dao.EventDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import com.tylerjohnson.eventcrafter.model.Event;

/**
 * Servlet responsible for handling event search and requests. 
 * This servlet processes user inputs, retrieves matching events from the database,
 * and forwards the result to the event list JSP.
 * 
 * 
 * @author tyler
 */
@WebServlet("/search-events")
public class SearchEventsServlet extends HttpServlet {

    /**
     * Handles POST requests for searching and filtering events.
     * Extracts search criteria from request parameters, queries the database,
     * and forwards results to the event list page.
     * 
     * @param request  The HTTP request containing search filters.
     * @param response The HTTP response used to forward search results.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve search parameters from the request
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String startDateStr = request.getParameter("start_date");
        String endDateStr = request.getParameter("end_date");
        
        // Convert date parameters if provided
        LocalDate startDate = (startDateStr != null && !startDateStr.isEmpty()) ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = (endDateStr != null && !endDateStr.isEmpty()) ? LocalDate.parse(endDateStr) : null;
        
        // Query the database for events matching the search criteria
        List<Event> filterEvents = EventDAO.searchAndFilterEvents(title, category, startDate, endDate);
        
        // Attach the filtered event list to the request attributes
        request.setAttribute("events", filterEvents);
        
        // Forward request to the event-list JSP page for rendering
        request.getRequestDispatcher("event-list.jsp").forward(request, response);
    }

    /**
     * Returns a brief description of the servlet.
     * @return A string describing the servlet's functionality.
     */    
    @Override
    public String getServletInfo() {
        return "Handles event search and filtering basedd on user input.";
    }

}
