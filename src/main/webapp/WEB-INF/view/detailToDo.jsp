<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>

<template:base>
    <jsp:attribute name="title">
        Detailansicht
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">
                    <div class="row">
                        <div class="col-md-9 float-left"> <output type="titel" name="titel"><b>${todo.name}</b></div>
                        <form method="post" class="stacked">
                            <div class="side-by-side float-right">
                                <button type="submit" class="btn btn-labeled btn-dark" name="action" value="edit">
                                    <span class="btn-label"><i class="fas fa-edit"></i></span>  Edit
                                </button>
                                <button type="submit" class="btn btn-labeled btn-danger" name="action" value="delete">
                                    <span class="btn-label"><i class="fas fa-trash-alt"></i></span>  Delete
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
                            <output type="titel" name="titel"> <b>Bearbeiter:</b> </output>
                                <c:forEach items="${users}" var="user" varStatus="loop">
                                <output type="titel" name="titel">${user.username}</output>
                                <c:if test="${!loop.last}">,</c:if>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel"><b>Fälligkeitsdatum:</b><fmt:formatDate pattern = "dd.MM.yyyy" value = "${todo.dueDate}"/></output>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel"><b>Fälligkeitszeit:</b><fmt:formatDate pattern = "HH:mm" value = "${todo.dueTime}"/></output>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-label-group">
                            <output type="titel" name="titel"><b>Beschreibung:</b> ${todo.description}</output>
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
            </div>
        </div>

        <%-- Kommentare --%>
        <div class="container chat-container mt-7">
            <c:forEach items="${comments}" var="comment">
                <div class="chat ${pageContext.request.userPrincipal.name eq comment.user.username ? 'me' : 'you'}">
                    <p>${comment.commentText}</p>
                    <span class="time"><i class="far fa-clock mr-2 ml-3"></i><fmt:formatDate pattern = "dd.MM.yyyy HH:mm" value = "${comment.commentTimestamp}"/></span>
                    <span class="time"><i class="far fa-user mr-2"></i>${comment.user.username}</span>
                </div>
            </c:forEach>
            <hr class="bg-light">
            <form method="post" class="stacked">
                <div class="d-flex justify-content-center mt-6 mb-5">
                    <div class="col">
                        <div class="row justify-content-center">
                            <textarea class="form-control comment-textarea" type="text" name="todo_comment" value="${todo_form.values["todo_comment"][0]}"></textarea>
                        </div>
                        <div class="row justify-content-center">
                            <button type="submit" class="btn btn-labeled btn-dark mt-1" name="action" value="comment">
                                <span class="btn-label"><i class="fas fa-comments"></i></span>  Kommentieren
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>
