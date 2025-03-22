package com.tylerjohnson.eventcrafter.servlets;

import com.tylerjohnson.eventcrafter.db.DatabaseConnection;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        if (username == null || password == null || email == null ||
                username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            request.setAttribute("error", "Please fill out all fields");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        if (DatabaseConnection.userExists(username)) {
            request.setAttribute("error", "Username already taken");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        if (DatabaseConnection.registerUser(username, password, email)) {
            request.setAttribute("message", "Registration successful. Please log in.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Registers a user";
    }

}
