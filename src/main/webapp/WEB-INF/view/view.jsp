<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
        <div id="categoryContainer" class="mx-auto categoryContainer">
            <c:forEach items="${categories}" var="category">

                <div class="card row bg-light mb-5">
                    <div class="card-header bg-light" id="heading${category.categoryName}">
                        <a class="nav-link" data-toggle="collapse" data-target="#collapse${category.categoryName}" aria-expanded="true" aria-controls="collapse${category.categoryName}">
                            ${category.categoryName}
                        </a>
                    </div>

                    <div id="collapse${category.categoryName}" class="collapse show" aria-labelledby="heading${category.categoryName}" data-parent="#categoryContainer">
                        <div class="row p-0 m-0">
                            <c:forEach items="${statuses}" var="status" varStatus="statusloop">
                                <div class="card col bg-secondary ml-1 mr-1">
                                    <div class="card-body pl-0 pr-0">
                                        <h5 class="card-title text-light">${status.label}</h5>
                                        <c:forEach items="${dashboard[category.categoryName][status.label]}" var="todo" varStatus="itemloop">
                                            <div class="card bg-light mt-1 mb-1">
                                                <div class="row mr-0">
                                                    <div class="col-md-1 centered my-auto">
                                                        <c:if test="${not statusloop.first}">
                                                            <form method="post" id="backForm">
                                                                <input type="text" name="back" value="${todo.id}" class="d-none">
                                                                <a href="#" onclick="document.getElementById('backForm').submit();"><i class="fas fa-arrow-left"></i></a>
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
                                                                <span class="time"><i class="fas fa-calendar-alt mr-2"></i>${todo.dueDate}</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-1 centered my-auto">
                                                        <c:if test="${not statusloop.last}">
                                                            <form method="post" id="forwardForm">
                                                                <input type="text" name="forward" value="${todo.id}" class="d-none">
                                                                <a href="#" onclick="document.getElementById('forwardForm').submit();"><i class="fas fa-arrow-right"></i></a>
                                                            </form>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </div>
    </jsp:attribute>
</template:base>
