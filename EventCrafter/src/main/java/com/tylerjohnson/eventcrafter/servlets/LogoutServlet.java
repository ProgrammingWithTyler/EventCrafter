package com.tylerjohnson.eventcrafter.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get session, don't create new
        
        if (session != null) {
            LOGGER.log(Level.INFO, "User logged out. Session invalidated.");
            session.invalidate(); // Destory session
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    public String getServletInfo() {
        return "Handles user logout and session termination";
    }

}
