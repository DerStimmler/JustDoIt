<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">Register</jsp:attribute>
    <jsp:attribute name="menu">

    </jsp:attribute>
    <jsp:attribute name="main">
        <div class="container">
            <h1>Register</h1>
            <form method="POST">
                <input type="text" name="name" placeholder="Name" value="${register_form.name}"/>
                <input type="text" name="email" placeholder="E-Mail-Adresse" value="${register_form.email}"/>
                <input type="text" name="password" placeholder="Password" value="${register_form.password}"/>
                <button type="submit">Registrieren</button>
            </form>

            <c:if test="${!empty register_form.errors}">
                <p>
                    <c:forEach items="${register_form.errors}" var="error">
                        ${error}<br>
                    </c:forEach>
                </p>
            </c:if>
        </div>
    </jsp:attribute>
</template:base>
