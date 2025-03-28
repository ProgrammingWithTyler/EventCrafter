<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@page import="java.util.List"%>
<%@page import="jakarta.servlet.http.HttpSession" %>
<%@page import="jakarta.servlet.http.HttpServletRequest" %>
<%@page import="com.tylerjohnson.eventcrafter.model.Event" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Dashboard"/>
</jsp:include>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
    <c:remove var="message" scope="session"/>
</c:if>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
    <c:remove var="error" scope="session"/>
</c:if>

<div class="d-flex justify-content-end m-3">
    <a href="${pageContext.request.contextPath}/events" class="btn btn-primary">View Events</a>
    <a href="${pageContext.request.contextPath}/create-event" class="btn btn-primary">Create Event</a>
</div>

<h2>Registered Events:</h2>
<c:if test="${not empty registeredEvents}">
    <ul class="list-unstyled">
        <c:forEach var="event" items="${registeredEvents}">
            <li>
                <a class="link-offset-2 link-offset-3-hover link-underline link-opacity-0 link-underline-opacity-75-hover" href="${pageContext.request.contextPath}/event-details?id=${event.id}">${event.title}</a>
            </li>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${empty registeredEvents}">
    <div class="alert alert-warning">
        <p>You have not registered for any events yet.</p>
    </div>
</c:if>

<jsp:include page="footer.jsp"/>
