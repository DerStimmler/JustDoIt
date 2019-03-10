<%--
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Anmeldung nicht möglich
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container col-md-4">
            <div class="card card-login mx-auto mt-5">
                <div class="row card-header justify-content-center">Benutzerdaten nicht korrekt!</div>
                <div class="row card-body justify-content-center">
                    <a class="btn btn-labeled btn-dark" href="<c:url value="/login/"/>">Erneut versuchen </a>
                </div>
            </div>
        </div>
    </jsp:attribute>
</template:base>