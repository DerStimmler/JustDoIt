<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Hier koennten Ihre Aufgaben stehen!
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="main">
        <div id="accordion">
            <div class="card col col-md-12">
                <div class="card-header" id="headingOne">
                    <h5 class="mb-0">
                        <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            DHBW
                        </button>
                    </h5>
                </div>

                <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                    <div class="row">
                        <div class="card col col-md-4">
                            <div class="card-body">
                                <h5 class="card-title">Offen</h5>
                                <p class="card-text">Liste der ToDos mit Name, Kreis mit Farbe für Priorität, Fälligkeitsdatum</p>
                            </div>
                        </div>
                        <div class="card col col-md-4">
                            <div class="card-body">
                                <h5 class="card-title">In Bearbeitung</h5>
                                <p class="card-text">Liste der ToDos mit Name, Kreis mit Farbe für Priorität, Fälligkeitsdatum</p>
                            </div>
                        </div>
                        <div class="card col col-md-4">
                            <div class="card-body">
                                <h5 class="card-title">Abgebrochen</h5>
                                <p class="card-text">Liste der ToDos mit Name, Kreis mit Farbe für Priorität, Fälligkeitsdatum</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card col col-md-12">
                <div class="card-header" id="headingOne">
                    <h5 class="mb-0">
                        <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            Privat
                        </button>
                    </h5>
                </div>

                <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                    <div class="row">
                        <div class="card col col-md-4">
                            <div class="card-body">
                                <h5 class="card-title">Offen</h5>
                                <p class="card-text">Liste der ToDos mit Name, Kreis mit Farbe für Priorität, Fälligkeitsdatum</p>
                            </div>
                        </div>
                        <div class="card col col-md-4">
                            <div class="card-body">
                                <h5 class="card-title">In Bearbeitung</h5>
                                <p class="card-text">Liste der ToDos mit Name, Kreis mit Farbe für Priorität, Fälligkeitsdatum</p>
                            </div>
                        </div>
                        <div class="card col col-md-4">
                            <div class="card-body">
                                <h5 class="card-title">Abgebrochen</h5>
                                <p class="card-text">Liste der ToDos mit Name, Kreis mit Farbe für Priorität, Fälligkeitsdatum</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </jsp:attribute>
    </template:base>
