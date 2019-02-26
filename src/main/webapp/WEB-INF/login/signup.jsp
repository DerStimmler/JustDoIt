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
                        <input type="text" class="form-control" name="username" value="${signup_form.values["username"][0]}" placeholder="Benutzername" required="required" autofocus="autofocus">
                    </div>

                    <div class="form-group">
                        <label for="password1">Passwort:<span class="required">*</span></label>
                        <input type="password" class="form-control" name="password1" value="${signup_form.values["password1"][0]}" placeholder="Passwort" required="required">
                    </div>

                    <div class="form-group">
                        <label for="password2">Passwort wiederholen:<span class="required">*</span></label>
                        <input type="password" class="form-control" name="password2" value="${signup_form.values["password2"][0]}" placeholder="Passwort wiederholen" required="required">
                    </div>

                    
                        <label for="email">E-Mail:<span class="required">*</span></label>
                        <input type="email" class="form-control" name="email" value="${signup_form.values["email"][0]}" placeholder="E-Mail" required="required">
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