<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@page session="true" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Home"/>
</jsp:include>

<c:if test="${not empty param.message}">
    <div class="alert alert-info">${param.message}</div>
</c:if>

<div class="container">
    <h1>Welcome to EventCrafter</h1>

    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <p class="lead">Hello, <strong>${sessionScope.user}</strong>! Ready to explore events?</p>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">View Events</a>
        </c:when>
        <c:otherwise>
            <p class="lead">Join EventCrafter today and discover amazing events!</p>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-success">Login</a>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp"/>