<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<template:base>
    <jsp:attribute name="title">
        Detailansicht des ToDo
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/delete.css"/>">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">
                    <div class="row">
                        <div class="col-md-8"> <c:forEach items="${todo.name}"> <c:out value="${todo.name}"/> </c:forEach></div>
                            <form method="post" class="stacked">
                                <div class="side-by-side">
                                    <button type="button" class="btn btn-labeled btn-danger" name="action" value="delete">
                                        <span class="btn-label"><i class="fas fa-trash-alt"></i></span>  Delete
                                    </button>
                                </div>
                                <div class="col-md-2 side-by-side">
                                    <button class="icon-pencil" name="action" value="edit" type="submit">
                                        Edit
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                <%-- Inhalt ToDo --%>
                <div class="card-body">
                    <%-- Ausgabefelder --%>
                    <div  class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel">Bearbeiter:${todo.user}</output>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel">Fälligkeitsdatum:${todo.dueDate}</output>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel">Beschreibung:${todo.description}</output>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%-- Fehlermeldungen --%>
        <c:if test="${!empty change_form.errors}">
            <ul class="errors">
                <c:forEach items="${change_form.errors}" var="error">
                    <li>${error}</li>
                    </c:forEach>
            </ul>
        </c:if>
    </jsp:attribute>
</template:base>