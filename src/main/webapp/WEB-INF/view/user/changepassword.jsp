<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Passwort ändern
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">Passwort ändern</div>
                <div class="card-body">
                    <form method="post" class="stacked">
                        <%-- Eingabefelder --%>
                        <div  class="form-group">
                            <div class="form-label-group">
                                <input type="password" class="form-control" name="password0" value="${change_form.values['password0'][0]}" placeholder="aktuelles Passwort" required="required" autofocus="autofocus">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-row">
                                <div class="col-md-6">
                                    <div class="form-label-group">
                                        <input type="password" class="form-control" name="password1" value="${change_form.values['password1'][0]}" placeholder="neues Passwort" required="required">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-label-group">
                                        <input type="password" class="form-control" name="password2" value="${change_form.values['password2'][0]}" placeholder="neues Passwort wiederholen" required="required">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- Button zum Abschicken --%>
                        <input class="btn btn-primary btn-block" type="submit" value="Ändern">
                    </form>
                </div>
                <%-- Fehlermeldungen --%>
                <c:if test="${!empty change_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${change_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>

    </jsp:attribute>
</template:base>