<%--
    Document   : base
    Created on : 21.02.2019, 22:18:34
    Author     : Goeller
--%>

<%@tag pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@attribute name="title"%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="menu" fragment="true"%>
<%@attribute name="main" fragment="true"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">

        <title>JustDoIt | ${title}</title>

        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap-reboot.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap-grid.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" />

        <!--        <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
                <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
                <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
                <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>-->

        <jsp:invoke fragment="head"/>

    </head>
    <body>
        <!-- Menü der Seite -->
        <nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light">
            <span class="navbar-brand mb-0 h1">JustDoIt</span>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <jsp:invoke fragment="menu"/>
                </ul>

                <form action="<c:url value="/logout/"/>">
                    <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Logout</button>
                </form>

            </div>
        </nav>

        <!-- Hauptinhalt der Seite -->
        <main class="bg-dark">
            <jsp:invoke fragment="main"/>
        </main>

        <!-- Footer der Seite -->
        <footer class="page-footer fixed-bottom font-small bg-light">
            <div class="footer-copyright text-center">JustDoIt © 2019
            </div>
        </footer>

        <!-- Skripte für Bootstrap -->
        <script type="text/javascript" src="<c:url value="/js/jquery.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/bootstrap.min.js"/>"></script>
    </body>
</html>

