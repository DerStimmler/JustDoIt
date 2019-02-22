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
        <link rel="stylesheet" href="<c:url value="/style.css"/>" />

        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <!--Menü der Seite -->
        <menu>
            <jsp:invoke fragment="menu"/>
        </menu>
        <!-- Hauptinhalt der Seite -->
        <main>    
            <jsp:invoke fragment="main"/>
        </main>
    </body>
</html>

