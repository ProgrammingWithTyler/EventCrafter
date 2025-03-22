<%@page contentType="text/html;UTF-8" language="java"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Register"/>
</jsp:include>


<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
            <h2 class="text-center mb-4">Register</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-primary">Register</button>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-secondary">Login</a>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>