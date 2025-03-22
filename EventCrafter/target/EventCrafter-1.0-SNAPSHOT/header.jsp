<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <c:choose>
                <c:when test="${not empty pageTitle}">${pageTitle}</c:when>
                <c:otherwise>EventCrafter</c:otherwise>
            </c:choose>
        </title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">EventCrafter</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <c:if test="${not empty sessionScope.user}">
                        <ul class="navbar-nav me-auto">
                            <li class="navbar-item ${pageContext.request.servletPath == '/dashboard' ? 'active' : ''}">
                                <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">Home</a>
                            </li>
                            <li class="navbar-item ${pageContext.request.servletPath == '/events' ? 'active' : ''}">
                                <a class="nav-link" href="${pageContext.request.contextPath}/events">Events</a>
                            </li>
                            <li class="navbar-item ${ageContext.request.servletPath == '/about' ? 'active' : ''}">
                                <a class="nav-link" href="${pageContext.request.contextPath}/about">About</a>
                            </li>
                        </ul>
                    </c:if>

                    <ul class="navbar-nav">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <li class="navbar-item">
                                    <span class="navbar-text text-light mt-3">Welcome ${sessionScope.user}!</span>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link text-danger" href="${pageContext.request.contextPath}/logout">Logout</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item ms-auto">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container mt-4">