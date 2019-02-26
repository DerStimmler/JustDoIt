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
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap-reboot.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap-grid.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" />

        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <!-- Header der Seite -->
        <header>

        </header>

        <!-- Menü der Seite -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <span class="navbar-brand mb-0 h1">JustDoIt</span>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <jsp:invoke fragment="menu"/>
                </ul>
            </div>
        </nav>

        <!-- Hauptinhalt der Seite -->
        <main class="bg-dark">
            <jsp:invoke fragment="main"/>
        </main>

        <!-- Footer der Seite -->
        <footer>
            JustDoIt
        </footer>
    </body>
</html>

