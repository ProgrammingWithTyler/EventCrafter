<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="${event.title}"/>
</jsp:include>

<c:if test="${empty event}">
    <c:choose>
        <c:when test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:when>
        <c:otherwise>
            <c:redirect url="events">
                <c:param name="error" value="Event not found"/>
            </c:redirect>
        </c:otherwise>
    </c:choose>
</c:if>

<div class="container p-4">
    <h2>Event Details</h2>
    <p><strong>Title:</strong> ${event.title}</p>
    <p><strong>Location:</strong> ${event.location}</p>
    <p><strong>Description:</strong> ${event.description}</p>
    <p><strong>Date:</strong> ${event.date}</p>
    <p><strong>Organizer:</strong> ${event.organizer}</p>
    
    <a class="link-offset-2 link-offset-3-hover link-underline link-opacity-0 link-underline-opacity-75-hover" href="${pageContext.request.contextPath}/dashboard">Back to Dashboard</a> |
    <a class="link-offset-2 link-offset-3-hover link-underline link-opacity-0 link-underline-opacity-75-hover" href="${pageContext.request.contextPath}/edit-event?id=${event.id}">Edit Event</a> |
    <a class="link-offset-2 link-offset-3-hover link-underline link-opacity-0 link-underline-opacity-75-hover" href="${pageContext.request.contextPath}/delete-event?id=${event.id}" onclick="return confirm('Are you sure?')">Delete Event</a>
</div>

    <jsp:include page="footer.jsp"/>