package com.tylerjohnson.eventcrafter.servlets;

import com.tylerjohnson.eventcrafter.db.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tyler
 */
@WebServlet(name = "TestConnectionServlet", urlPatterns = {"/TestConnection"})
public class TestConnectionServlet extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try (Connection conn = DatabaseConnection.getConnection()) {
            out.println("<h1>Database Connection Successful!</h1>");
        } catch (SQLException e) {
            out.println("<h1>Database Connection Successful!</h1>");
            e.printStackTrace();
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
