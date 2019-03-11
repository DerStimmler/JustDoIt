<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Kategorien
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <form method="post" class="stacked">
                <%-- Eingabefelder --%>
                <div class="card card-register mx-auto mt-5">
                    <div class="card-header">Kategorie erstellen</div>
                    <div class="card-body">
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">
                                    <i class="fas fa-star"></i>
                                </span>
                            </div>
                            <input type="text" class="form-control" name="category_name" value="${category_form.values["category_name"][0]}" autofocus="autofocus" placeholder="Kategorie">
                        </div>

                        <%-- Button zum Abschicken --%>
                        <div class="side-by-side">
                            <button class="btn btn-primary btn-block mb-2" name="action" value="create" type="submit">
                                Kategorie erstellen
                            </button>
                        </div>
                    </div>
                </div>
                <div class="card card-register mx-auto mt-2">
                    <div class="card-header">Existierende Kategorien</div>
                    <div class="card-body">
                        <div class="text-center mb-1">
                            <%-- Vorhandene Kategorien --%>
                            <c:choose>
                                <c:when test="${empty categories}">
                                    Es sind noch keine Kategorien vorhanden.
                                </c:when>
                                <c:otherwise>
                                    <div class="col">
                                        <c:forEach items="${categories}" var="category">
                                            <c:if test="${category.categoryName != 'Ohne Kategorie'}">
                                                <div class="row">
                                                    <div class="custom-control custom-checkbox offset-md-5">
                                                        <input type="checkbox" class="custom-control-input" name="category" value="${category.categoryName}" id="${'category'.concat(category.id)}">
                                                        <label class="custom-control-label" for="${'category'.concat(category.id)}">${category.categoryName}</label>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="side-by-side">
                                        <button class="btn btn-primary btn-block mt-4" name="action" value="delete" type="submit">
                                            Ausgewählte Kategorien löschen
                                        </button>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <%-- Fehlermeldungen --%>
                            <c:if test="${!empty category_form.errors}">
                                <ul class="errors">
                                    <c:forEach items="${category_form.errors}" var="error">
                                        <li>${error}</li>
                                        </c:forEach>
                                </ul>
                            </c:if>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>
