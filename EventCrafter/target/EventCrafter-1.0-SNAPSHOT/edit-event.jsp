<%@page import="com.tylerjohnson.eventcrafter.dao.EventDAO" %>
<%@page import="com.tylerjohnson.eventcrafter.model.Event" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="${event.title}"/>
</jsp:include>

<c:if test="${empty event}">
    <c:redirect url="dashboard.jsp">
        <c:param name="error" value="Event not found"/>
    </c:redirect>
</c:if>

<div class="container p-4">
    <h2>Edit Event</h2>
    <form action="${pageContext.request.contextPath}/events" method="post" class="mt-3">
        <input type="hidden" name="id" value="${event.id}">
        
        <div class="mb-3">
            <label for="title" class="form-label">Title:</label>
            <input type="text" id="title" name="title" class="form-control" value="${event.title}" required>
        </div>
        
        <div class="mb-3">
            <label for="description" class="form-label">Description:</label>
            <textarea id="description" name="description" class="form-control" required>${event.description}</textarea>
        </div>
        
        <div class="mb-3">
            <label for="location" class="form-label">Location:</label>
            <input type="text" id="location" name="location" class="form-control" value="${event.location}" required>
        </div>
        
        <div class="mb-3">
            <label for="date" class="form-label">Date:</label>
            <input type="date" id="date" name="date" class="form-control" value="${event.date}" required>
        </div>
        
        <button type="submit" class="btn btn-primary">Save Changes</button>
    </form>
</div>

<jsp:include page="footer.jsp"/>