<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Erstellen
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">ToDo erstellen</div>
                <div class="card-body">
                    <form method="post" class="stacked">
                        <%-- Eingabefelder --%>
                        <div class="form-group">
                            <div class="form-label-group">
                                <select class="js-example-basic-multiple col-md-10 form-control" name="todo_user" multiple="multiple" id="usernameSelectCreateToDo" required="required" placeholder="Benutzer">
                                    <c:forEach items="${users}" var="user">
                                        <option value="${user.getUsername()}">${user.getUsername()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-label-group">
                                <input type="text" class="form-control" name="todo_title" value="${todo_form.values["todo_title"][0]}" autofocus="autofocus" required="required" placeholder="Titel">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-label-group">
                                <input type="text" class="form-control" name="todo_description" value="${todo_form.values["todo_description"][0]}" required="required" placeholder="Beschreibung">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-row">
                                <div class="col-md-6">
                                    <div class="form-label-group">
                                        <input type="date" class="form-control" name="todo_due_date" value="${todo_form.values["todo_due_date"][0]}" required="required" placeholder="Fällligkeitsdatum">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-label-group">
                                        <input type="time" class="form-control" name="todo_due_time" value="${todo_form.values["todo_due_time"][0]}" required="required" placeholder="Fälligkeitszeit">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-label-group">
                                <select class="form-control" name="todo_category" required="required" placeholder="Kategorie">
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.categoryName}">${category.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-label-group">
                                <select class="form-control" name="todo_priority" required="required" placeholder="Priorität">
                                    <c:forEach items="${priorities}" var="priority">
                                        <option value="${priority}">${priority.label}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <%-- Button zum Abschicken --%>
                        <button class="btn btn-primary btn-block" name="action" value="create" type="submit">
                            ToDo erstellen
                        </button>
                    </form>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty todo_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${todo_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>
    </jsp:attribute>
</template:base>
