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
        <c:forEach items="${categories}" var="category">
            <div id="dataParent">
                <div class="card col col-md-12">
                    <div class="card-header" id="heading${category.categoryName}">
                        <h5 class="mb-0">
                            <button class="btn btn-link" data-toggle="collapse" data-target="#collapse${category.categoryName}" aria-expanded="true" aria-controls="collapse${category.categoryName}">
                                ${category.categoryName}
                            </button>
                        </h5>
                    </div>

                    <div id="collapse${category.categoryName}" class="collapse show" aria-labelledby="heading${category.categoryName}">
                        <div class="row">
                            <c:forEach items="${statuses}" var="status" >
                                <div class="card col col-md-3">
                                    <div class="card-body">
                                        <h5 class="card-title">${status.label}</h5>
                                        <c:forEach items="${dashboard[category.categoryName][status.label]}" var="todo">
                                            <div class="row">
                                                <a href="${pageContext.request.contextPath}/view/todo/detail/${todo.id}" class="col col-md-7 href">${todo.name}</a>
                                                <span class="${todo.priority} col col-md-1"></span>
                                                <span class="col col-md-4">${todo.dueDate}</span>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </jsp:attribute>
</template:base>
