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
        <c:choose>
            <c:when test="${!empty errors}">
                <ul class="errors">
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <div class="container">
                    <div class="card card-register mx-auto mt-5">
                        <div class="card-header">Aktivierung Erfolgreich</div>
                        <div class="card-body">
                            <div class="text-center mb-4">
                                <h4>Ihr Account wurde erfolgreich aktiviert!</h4>
                            </div>
                            <div class="text-center">
                                <a class="d-block small mt-3" href="<c:url value="/view/dashboard/"/>">Zum Login</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>