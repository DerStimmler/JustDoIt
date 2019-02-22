<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">Login</jsp:attribute>
    <jsp:attribute name="menu">

    </jsp:attribute>
    <jsp:attribute name="main">
        <div class="container">
            <h1>Login</h1>
            <form method="POST">
                <input type="text" name="name" placeholder="Name" value="${login_form.name}"/>
                <input type="text" name="password" placeholder="Password" value="${login_form.password}"/>
                <input type="checkbox" name="rememberMe" value="true" <c:if test="${login_form.rememberMe eq true}">checked</c:if>>
                    <button type="submit">Anmelden</button>
                </form>

            <c:if test="${!empty login_form.errors}">
                <p>
                    <c:forEach items="${login_form.errors}" var="error">
                        ${error}<br>
                    </c:forEach>
                </p>
            </c:if>
        </div>
    </jsp:attribute>
</template:base>

