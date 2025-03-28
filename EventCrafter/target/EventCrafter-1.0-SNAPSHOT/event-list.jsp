<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Events"/>
</jsp:include>

<div class="row">
    <form action="${pageContext.request.contextPath}/search-events" method="post">
        <div class="container mb-3">
            <div class="row">
                <div class="col-12">
                    <div class="form-group mb-2 mr-2">
                        <input type="text" class="form-control" name="title" placeholder="Search by title"/>
                    </div>
                    <div class="form-group mb-2 mr-2">
                        <input type="text" class="form-control" name="category" placeholder="Category"/>
                    </div>
                    <div class="form-group mb-2 mr-2">
                        <label for="start_date" class="sr-only">Start Date</label>
                        <input type="date" class="form-control" name="start_date" />
                    </div>
                    <div class="form-group mb-2 mr-2">
                        <label for="end_date" class="sr-only">End Date</label>
                        <input type="date" class="form-control" name="end_date" />
                    </div>
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </div>
        </div>
</div>
</form>
<table class="table table-bordered table-hover">
    <thead>
        <tr>
            <th>Title</th>
            <th>Location</th>
            <th>Date</th>
            <th>Attendees</th>
            <th>Organizer</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="event" items="${events}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/event-details?id=${event.id}">${event.title}</a></td>
                <td>${event.location}</td>
                <td>${event.date}</td>
                <td>${event.attendees}</td>
                <td>${event.organizer}</td>
                <td>
                    <form action="register-event" method="post">
                        <input type="hidden" name="eventId" value="${event.id}">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<nav>
    <ul class="pagination">
        <c:choose>
            <c:when test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="events?page=${currentPage - 1}">
                        <span>&laquo;</span>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <span class="page-link">&laquo;</span>
                </li>
            </c:otherwise>
        </c:choose>

        <c:forEach var="i" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${currentPage == i}">
                    <li class="page-item active">
                        <span class="page-link">${i}</span>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="events?page=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="events?page=${currentPage + 1}">
                        <span>&raquo;</span>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <span class="page-link">&raquo;</span>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>
</div>

<jsp:include page="footer.jsp"/>