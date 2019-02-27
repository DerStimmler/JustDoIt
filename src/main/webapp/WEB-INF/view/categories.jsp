<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Kategorien
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div>
            <form method="post" class="stacked">
                <div class="column">

                    <%-- Eingabefelder --%>
                    <label for="category_name">
                        Kategorie:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="category_name" value="${category_form.values["category_name"][0]}" autofocus="autofocus">
                    </div>

                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" name="action" value="create" type="submit">
                            Kategorie erstellen
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty category_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${category_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>

                <%-- Vorhandene Kategorien --%>
                <c:choose>
                    <c:when test="${empty categories}">
                        Es sind noch keine Kategorien vorhanden.
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${categories}" var="category">
                            <div class="row">
                                <%--TODO: value und id anpassen, sobald category primary key angepasst wird --%>
                                <input type="checkbox" name="category" value="${category.categoryName}" id="${'category'.concat(category.id)}">
                                <label for="${'category'.concat(category.id)}">${category.categoryName}</label>
                            </div>
                        </c:forEach>
                        <div class="side-by-side">
                            <button class="icon-pencil" name="action" value="delete" type="submit">
                                Ausgewählte Kategorien löschen
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </jsp:attribute>
</template:base>
