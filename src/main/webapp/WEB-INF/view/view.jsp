<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>

<template:base>
    <jsp:attribute name="title">
        JustDoIt - Dashboard
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/view.css"/>">
    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <form method="post" id="searchToDo">
            <div class="row mx-auto categoryContainer justify-content-end mb-3">
                <select class="js-example-basic-single form-control w-25" name="searchToDo" id="searchToDoSelect">
                    <option></option>
                    <c:forEach items="${todos}" var="todo">
                        <option value="${todo.id}">${todo.name}</option>
                    </c:forEach>
                </select>
                <a href="#" onclick="document.getElementById('searchToDo').submit();"><i class="fas fa-search fa-2x ml-2"></i></a>
            </div>
        </form>
        <div id="categoryContainer" class="mx-auto categoryContainer">
            <c:forEach items="${categories}" var="category" varStatus="categoryLoop">
                <div class="card row bg-light mb-5 flex-nowrap pb-1">
                    <div class="card-header bg-light" id="heading${categoryLoop.index}">
                        <a class="nav-link font-weight-bold" data-toggle="collapse" data-target="#collapse${categoryLoop.index}" aria-expanded="false" aria-controls="collapse${categoryLoop.index}">
                            <i class="fas fa-star mr-2"></i>${category}
                            <div class="progress">
                                <c:set var="total" value="${0}"></c:set>
                                <c:forEach items="${statuses}" var="status">
                                    <c:set var="total" value="${total + fn:length(dashboard[category][status.label])}"></c:set>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${total eq 0}">
                                        <div class="progress-bar" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${statuses}" var="status" varStatus="statusloop">
                                            <div class="progress-bar ${statusColors[statusloop.index]}" role="progressbar" style="width: ${100/total*fn:length(dashboard[category][status.label])}%" aria-valuenow="${fn:length(dashboard[category][status.label])}" aria-valuemin="0" aria-valuemax="${total}">${fn:length(dashboard[category][status.label])}</div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </a>
                    </div>
                    <div id="collapse${categoryLoop.index}" class="collapse" aria-labelledby="heading${categoryLoop.index}">
                        <div class="row p-0 m-0">
                            <!-- Prüfen ob keine ToDos gespeichert sind-->
                            <c:choose>
                                <c:when test="${empty dashboard[category]}">
                                    <div class="d-flex justify-content-center w-100">
                                        <div class="alert alert-info m-1 text-center w-50">
                                            Keine ToDo's vorhanden!
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${statuses}" var="status" varStatus="statusloop">
                                        <div class="card col bg-secondary ml-1 mr-1">
                                            <div class="card-body pl-0 pr-0">
                                                <h5 class="card-title text-light">${status.label}<span class="float-right badge badge-light">${fn:length(dashboard[category][status.label])}</span></h5>
                                                    <c:forEach items="${dashboard[category][status.label]}" var="todo" varStatus="itemloop">
                                                    <div class="card bg-light mt-1 mb-1">
                                                        <div class="row mr-0">
                                                            <div class="col-md-1 centered my-auto">
                                                                <c:if test="${not statusloop.first}">
                                                                    <form method="post" id="backForm${todo.id}">
                                                                        <input type="text" name="back" value="${todo.id}" class="d-none">
                                                                        <a href="#" onclick="document.getElementById('backForm${todo.id}').submit();"><i class="fas fa-arrow-left"></i></a>
                                                                    </form>
                                                                </c:if>
                                                            </div>
                                                            <div class="col">
                                                                <div class="row">
                                                                    <a href="${pageContext.request.contextPath}/view/todo/detail/${todo.id}" class="col col-md-7 href">${todo.name}</a>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col">
                                                                        <span class="${todo.priority}"></span>
                                                                    </div>
                                                                    <div class="col">
                                                                        <span class="time"><i class="fas fa-calendar-alt mr-2"></i><fmt:formatDate pattern = "dd.MM.yyyy" value = "${todo.dueDate}"/></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-1 centered my-auto">
                                                                <c:if test="${not statusloop.last}">
                                                                    <form method="post" id="forwardForm${todo.id}">
                                                                        <input type="text" name="forward" value="${todo.id}" class="d-none">
                                                                        <a href="#" onclick="document.getElementById('forwardForm${todo.id}').submit();"><i class="fas fa-arrow-right"></i></a>
                                                                    </form>
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <!-- Bemerkung anzeigen wenn keine Kategorien vorhanden sind-->
            <c:if test="${empty categories}">
                <div class="d-flex justify-content-center w-100 mt-5">
                    <div class="alert alert-info text-center center-block w-75 mt-5">Keine Kategorien vorhanden!</div>
                </div>
            </c:if>
        </div>
    </jsp:attribute>
</template:base>
