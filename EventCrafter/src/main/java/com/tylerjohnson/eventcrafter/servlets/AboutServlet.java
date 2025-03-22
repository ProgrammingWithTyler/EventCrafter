package com.tylerjohnson.eventcrafter.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles requests for the about view.
 * 
 * @author tyler
 */
@WebServlet("/about")
public class AboutServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("about.jsp").forward(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Handles requests for the about view.";
    }

}
