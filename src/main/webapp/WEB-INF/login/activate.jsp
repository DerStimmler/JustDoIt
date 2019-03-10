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
        <div class="text-center">
            Ihr Account wurde erfolgreich aktiviert!
        </div>
        <div class="text-center">
            <a class="d-block small mt-3" href="<c:url value="/view/dashboard/"/>">Zum Login</a>
        </div>
    </jsp:attribute>
</template:base>