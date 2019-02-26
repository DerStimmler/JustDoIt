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
        <div class="menuitem">
            <a href="<c:url value="/view/dashboard/"/>">Abbrechen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <h1>Benutzerdaten ändern</h1>
            <form method="post" class="stacked">
                    
                    <%-- Eingabefelder --%>
                        <div class="form-group">
                            <label for="password0">aktuelles Passwort:<span class="required">*</span></label>
                            <input type="password" class="form-control" name="password0" value="${changeMail_form.values["password0"][0]}" placeholder="aktuelles Passwort" required="required" autofocus="autofocus">
                         </div>
                         
                            <div class="form-group">
                                <label for="email">E-Mail-Adresse:<span class="required">*</span></label>
                                <input type="email" class="form-control" name="email" value="${changeMail_form.values["email"][0]}" placeholder="E-Mail-Adresse" required="required">
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
                <c:if test="${!empty changeMail_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${changeMail_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>