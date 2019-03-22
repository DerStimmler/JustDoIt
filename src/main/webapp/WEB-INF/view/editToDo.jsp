<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">ToDo bearbeiten</div>
                <div class="card-body">
                    <form method="post" class="stacked">
                        <%-- Eingabefelder --%>
                        <div class="form-group">
                            <div class="side-by-side">
                                <select class="js-example-basic-multiple col-md-10 form-control" name="todo_user" multiple="multiple" id="usernameSelectEditToDo" required="required">
                                    <c:forEach items="${users}" var="user">
                                        <option value="${user.getUsername()}">${user.getUsername()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="side-by-side">
                                <input class="form-control" type="text" name="todo_title" value="${todo.name}" autofocus="autofocus" required="required" placeholder="Titel">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="side-by-side">
                                <input class="form-control" type="text" name="todo_description" value="${todo.description}" autofocus="autofocus" required="required" placeholder="Beschreibung">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-row">
                                <div class="col-md-6">
                                    <input class="form-control" type="date" name="todo_due_date" value="${todo.dueDate}" autofocus="autofocus" required="required" placeholder="Fällligkeitsdatum">
                                </div>
                                <div class="col-md-6">
                                    <input class="form-control" type="time" name="todo_due_time" value="${todo.dueTime}" autofocus="autofocus" required="required" placeholder="Fälligkeitszeit">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="side-by-side">
                                <select class="form-control" name="todo_category" required="required" placeholder="Kategorie">
                                    <c:forEach items="${categories}" var="category">
                                        <c:choose>
                                            <c:when test="${(currentCategory eq null) && (category eq 'Keine Kategorie')}">
                                                <option value="${category}" selected>${category}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${category}" ${category eq currentCategory.categoryName ? ' selected' : ''}>${category}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="side-by-side">
                                <select class="form-control" name="todo_priority" required="required" placeholder="Priorität">
                                    <c:forEach items="${priorities}" var="priority">
                                        <option value="${priority}" ${priority eq todo.priority ? ' selected' : ''}>${priority.label}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <%-- Button zum Abschicken --%>
                        <button class="btn btn-primary btn-block" name="action" value="edit" type="submit">
                            ToDo speichern
                        </button>
                        <%-- "Button" zum Abbrechen --%>
                        <a href="${pageContext.request.contextPath}/view/dashboard/" class="btn btn-primary btn-block">Abbrechen</a>
                    </form>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty todo_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${todo_form.errors}" var="error">
                            <li>
                                ${error}
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                </form>
            </div>
        </div>
    </jsp:attribute>
</template:base>
