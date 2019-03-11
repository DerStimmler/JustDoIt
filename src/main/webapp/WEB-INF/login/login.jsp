<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Login
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-login mx-auto mt-5">
                <div class="card-header">Login</div>
                <div class="card-body">
                    <form action="j_security_check" method="post" class="stacked">
                        <%-- Eingabefelder --%>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-user"></i></span>
                            </div>
                            <input type="text" class="form-control" name="j_username" placeholder="Benutzername" required="required" autofocus="autofocus">
                        </div>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" class="form-control" name="j_password" placeholder="Passwort" required="required">
                        </div>
                        <%-- Button zum Abschicken --%>
                        <input class="btn btn-primary btn-block" type="submit" value="Einloggen">
                    </form>
                    <div class="text-center">
                        <a class="d-block small mt-3" href="<c:url value="/signup/"/>">Registrieren</a>
                        <a class="d-block small" href="<c:url value="/resetpw/"/>">Passwort vergessen?</a>
                        <a class="d-block small" href="<c:url value="/activation/resend/"/>">Aktivierungsmail erneut anfordern</a>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</template:base>
