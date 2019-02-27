<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Benutzerdaten ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <h1>Benutzerdaten ändern</h1>
            <form method="post" class="stacked">

                <%-- CSRF-Token --%>
<!--                    <input type="hidden" name="csrf_token" value="${csrf_token}">-->


                <%-- Eingabefelder --%>
                <div  class="form-group">
                    <label for="password0">aktuelles Passwort:<span class="required">*</span></label>
                    <input type="password" class="form-control" name="password0" value="${change_form.values['password0'][0]}" placeholder="aktuelles Passwort" required="required" autofocus="autofocus">
                </div>
                <div class="form-group">
                    <label for="password1">Passwort:<span class="required">*</span></label>
                    <input type="password" class="form-control" name="password1" value="${change_form.values['password1'][0]}" placeholder="Passwort" required="required">
                </div>
                <div class="form-group">
                    <label for="password2">Passwort wiederholen:<span class="required">*</span></label>
                    <input type="password" class="form-control" name="password2" value="${change_form.values['password2'][0]}" placeholder="Passwort wiederholen" required="required">
                </div>

                <%--
                    <label for="email">E-Mail:<span class="required">*</span></label>
                    <input type="email" class="form-control" name="email" value="${change_form.email}" placeholder="E-Mail">
                    <small id="emailHelp" class="form-text text-muted">We need your E-Mail-Adress to notify you about your taks.</small>
                --%>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <input class="btn btn-primary" type="submit" value="Ändern">
                </div>


                <%-- Fehlermeldungen --%>
                <c:if test="${!empty change_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${change_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>