<%@page import="com.tylerjohnson.eventcrafter.dao.EventDAO" %>
<%@page import="com.tylerjohnson.eventcrafter.model.Event" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Create Event"/>
</jsp:include>

<div class="container p-4">
    <h2>Create Event</h2>
    <form action="${pageContext.request.contextPath}/create-event" method="post" class="mt-3">
        
        <div class="mb-3">
            <label for="title" class="form-label">Title:</label>
            <input type="text" id="title" name="title" class="form-control" required>
        </div>
        
        <div class="mb-3">
            <label for="description" class="form-label">Description:</label>
            <textarea id="description" name="description" class="form-control" required></textarea>
        </div>
        
        <div class="mb-3">
            <label for="location" class="form-label">Location:</label>
            <input type="text" id="location" name="location" class="form-control" required>
        </div>
        
        <div class="mb-3">
            <label for="date" class="form-label">Date:</label>
            <input type="date" id="date" name="date" class="form-control" required>
        </div>
        
        <button type="submit" class="btn btn-primary">Create Event</button>
    </form>
</div>

<jsp:include page="footer.jsp"/>