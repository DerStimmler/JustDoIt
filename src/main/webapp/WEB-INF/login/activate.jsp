<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Aktivierung Ihres Accounts
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">Aktivierung Ihres Accounts</div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${!empty errors}">
                            <div class="text-center mb-4">
                                <h4>Bei der Aktivierung sind Fehler aufgetreten:</h4>
                            </div>
                            <ul class="errors">
                                <c:forEach items="${errors}" var="error">
                                    <li>${error}</li>
                                    </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center mb-4">
                                <h4>Ihr Account wurde erfolgreich aktiviert!</h4>
                            </div>
                            <div class="text-center">
                                <a class="d-block small mt-3" href="<c:url value="/view/dashboard/"/>">Zum Login</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </jsp:attribute>
</template:base>