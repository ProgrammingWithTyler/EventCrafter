<%@page import="com.tylerjohnson.eventcrafter.dao.EventDAO" %>
<%@page import="com.tylerjohnson.eventcrafter.model.Event" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Create Event"/>
</jsp:include>

<c:if test="${not empty sessionScope.message}">
    <div class="alert alert-success">${sessionScope.message}</div>
    <c:remove var="message" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.error}">
    <div class="alert alert-danger">${sessionScope.error}</div>
    <c:remove var="error" scope="session"/>
</c:if>

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
        
        <div class="mb-3">
            <label for="attendees" class="form-label">Attendees:</label>
            <input type="number" id="attendees" name="attendees" class="form-control" min="0" value="0" required>
        </div>
        
        <div class="mb-3">
            <label for="category" class="form-label">Category:</label>
            <select id="category" name="category" class="form-select" required>
                <option value="">-- Select Category --</option>
                <option value="Conference">Conference</option>
                <option value="Meetup">Meetup</option>
                <option value="Workshop">Workshop</option>
                <option value="Other">Other</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Create Event</button>
    </form>
</div>

<jsp:include page="footer.jsp"/>