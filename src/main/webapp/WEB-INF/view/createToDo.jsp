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
                    <label for="todo_user">
                        Bearbeiter:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <select name="todo_user">
                            <c:forEach items="${users}" var="user">
                                <option value="${user.getUsername()}">${user.getUsername()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <label for="todo_title">
                        Titel:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="todo_title" value="${todo_form.values["todo_title"][0]}" autofocus="autofocus">
                    </div>
                    <label for="todo_description">
                        Beschreibung:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="todo_description" value="${todo_form.values["todo_description"][0]}" autofocus="autofocus">
                    </div>
                    <label for="todo_due_date">
                        Fälligkeit:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="todo_due_date" value="${todo_form.values["todo_due_date"][0]}" autofocus="autofocus">
                        <input type="time" name="todo_due_time" value="${todo_form.values["todo_due_time"][0]}" autofocus="autofocus">
                    </div>
                    <label for="todo_category">
                        Kategorie
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <select name="todo_category">
                            <c:forEach items="${categories}" var="category">
                                <option value="${category.categoryName}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" name="action" value="create" type="submit">
                            ToDo erstellen
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty todo_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${todo_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>
