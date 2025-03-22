<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>--%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="About"/>
</jsp:include>

<header>
    <h1>About the Event Registration System</h1>
</header>

<main>
    <section>
        <h2>Project Overview</h2>
        <p>
            For my final project, I have chosen to develop an <strong>Event Registration System</strong>
            for conferences, workshops, and other events. This web application enables users to browse
            upcoming events, create accounts, log in, and register for events.
        </p>
    </section>

    <section>
        <h2>Core Features</h2>
        <ul>
            <li>Display a list of upcoming events stored in a MySQL database.</li>
            <li>Allow users to register for an account and log in.</li>
            <li>Restrict event registration  to logged-in users.</li>
            <li>Implement session management for authentication.</li>
            <li>Provide admin panel for event organizers to manage events.</li>
            <li>Ensure secure storage for user credentials.</li>
            <li>Enable users to log out and securely end their session.</li>
        </ul>
    </section>
    
    <section>
        <h2>Technical Implementation</h2>
        <p>
            The system is built using the following technologies:
        </p>
        <ul>
            <li><strong>Frontend:</strong> JSP, HTML, CSS</li>
            <li><strong>Backend:</strong> Java Servlets</li>
            <li><strong>Database:</strong> MySQL with JDBC</li>
            <li><strong>Session Management:</strong> HTTP sessions for authentication</li>
            <li><strong>Security:</strong> Password hashing and validation</li>
        </ul>
    </section>
    
    <section>
        <h2>Learning Goals</h2>
        <p>
            Through this project, I aim to strengthen my understanding of:
        </p>
        <ul>
            <li>Form hanlding in JSP and Servlets</li>
            <li>User authentication and session management</li>
            <li>CRUD operations with JDBC and MySQL</li>
            <li>Best practices for security in web applications</li>
        </ul>

    </section>

</main>

<jsp:include page="footer.jsp"/>