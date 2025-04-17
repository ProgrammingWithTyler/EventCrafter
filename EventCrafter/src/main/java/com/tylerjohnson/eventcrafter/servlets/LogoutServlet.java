package com.tylerjohnson.eventcrafter.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles user logout and session termination.
 *
 * @author tyler
 */
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

        // Expire the JSESSIONID cookie by setting a past expiration date
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0); // Set cookie expiration to 0, which deletes the cookie
        cookie.setPath(request.getContextPath());
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie); // Send the cookie to the client
        
        LOGGER.log(Level.INFO, "JSESSIONID cookie expired.");

        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    public String getServletInfo() {
        return "Handles user logout and session termination";
    }

}
