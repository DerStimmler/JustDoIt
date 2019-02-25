<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Registrierung
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/logout/"/>">Einloggen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <h1>Registrieren</h1>
            <form method="post" class="stacked">
                
                    <%-- CSRF-Token --%>
<!--                    <input type="hidden" name="csrf_token" value="${csrf_token}">-->

                    <%-- Eingabefelder --%>
                    <div class="form-group">
                        <label for="username">Benutzername:<span class="required">*</span></label>
                        <input type="text" class="form-control" name="username" value="${signup_form.username}" placeholder="Benutzername">
                    </div>

                    <div class="form-group">
                        <label for="password1">Passwort:<span class="required">*</span></label>
                        <input type="password" class="form-control" name="password1" value="${signup_form.password1}" placeholder="Passwort">
                    </div>

                    <div class="form-group">
                        <label for="password2">Passwort wiederholen:<span class="required">*</span></label>
                        <input type="password" class="form-control" name="password2" value="${signup_form.password2}" placeholder="Passwort wiederholen">
                    </div>

                    
                        <label for="email">E-Mail:<span class="required">*</span></label>
                        <input type="email" class="form-control" name="email" value="${signup_form.email}" placeholder="E-Mail">
                        <small id="emailHelp" class="form-text text-muted">We need your E-Mail-Adress to notify you about your taks.</small>
                    

                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <input class="btn btn-primary" type="submit" value="Registrieren">
                    </div>
                

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signup_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signup_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>