<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Passwort vergessen
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">Passwort zurücksetzen</div>
                <div class="card-body">
                    <div class="text-center mb-4">
                        <h4>Passwort vergessen?</h4>
                        <p>Gib deinen Benutzernamen ein und wir schicken dir eine E-Mail mit einem neuen Passwort.</p>
                    </div>
                    <form method="post" class="stacked">
                        <%-- Eingabefelder --%>
                        <div class="form-group">
                            <div class="form-label-group">
                                <input type="text" class="form-control" name="username" value="${resetpw_form.values["username"][0]}" placeholder="Benutzername" required="required" autofocus="autofocus">
                            </div>
                        </div>
                        <%-- Button zum Abschicken --%>
                        <input class="btn btn-primary btn-block" type="submit" value="Passwort zurücksetzen">
                    </form>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty resetpw_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${resetpw_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>

    </jsp:attribute>
</template:base>
