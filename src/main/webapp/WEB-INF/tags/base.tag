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

        <link rel="stylesheet" type="text/css" href="<c:url value="/css/select2/select2.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/fontawesome/css/all.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap-reboot.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap-grid.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap/bootstrap.min.css"/>" >
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" />

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

                    <!-- Navbar Items -->
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.requestURI eq '/justDoIt/WEB-INF/view/view.jsp' ? ' active' : ''}" href="<c:url value="/"/>">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.requestURI eq '/justDoIt/WEB-INF/view/categories.jsp' ? ' active' : ''}" href="<c:url value="/view/categories/"/>">Kategorien</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle <c:if test = "${fn:contains(pageContext.request.requestURI, '/justDoIt/WEB-INF/view/user/')}">active</c:if>" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Benutzerdaten ändern
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item ${pageContext.request.requestURI eq '/justDoIt/WEB-INF/view/user/changemail.jsp' ? ' active' : ''}" href="<c:url value="/view/user/changemail/"/>">E-Mail ändern</a>
                            <a class="dropdown-item ${pageContext.request.requestURI eq '/justDoIt/WEB-INF/view/user/changepassword.jsp' ? ' active' : ''}" href="<c:url value="/view/user/changepw/"/>">Passwort ändern</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.requestURI eq '/justDoIt/WEB-INF/view/createToDo.jsp' ? ' active' : ''}" href="<c:url value="/view/todo/create/"/>">ToDo erstellen</a>
                    </li>
                    <jsp:invoke fragment="menu"/>
                    <!-- /Navbar Items -->

                </ul>

                <!-- Login/Logout Button -->
                <c:choose>
                    <c:when test="${not empty pageContext.request.userPrincipal}">
                        <p class="my-2 my-sm-0 pr-2">${pageContext.request.userPrincipal.name}</p>
                        <form action="<c:url value="/logout/"/>">
                            <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Logout</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="<c:url value="/login/"/>">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Login</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>

        <!-- Hauptinhalt der Seite -->
        <main class="bg-dark">
            <jsp:invoke fragment="main"/>
        </main>

        <!-- Footer der Seite -->
        <footer class="page-footer fixed-bottom font-small bg-light">
            <div class="footer-copyright text-center">JustDoIt © 2019
                <a class="float-right text-right pr-5" target="_blank" href="https://github.com/DerStimmler/JustDoIt"><i class="fab fa-github" style="color:black"></i></a>
            </div>
        </footer>

        <!-- Skripte für Bootstrap -->
        <script type="text/javascript" src="<c:url value="/js/jquery/jquery.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/bootstrap/bootstrap.min.js"/>"></script>

        <!-- Skript für Select2 -->
        <script type="text/javascript" src="<c:url value="/js/select2/select2.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/select2/select2_multiple.js"/>"></script>
        <!-- Preselect Current User in CreateToDo -->
        <script>
            $('#usernameSelectCreateToDo').val('${pageContext.request.userPrincipal.name}');
            $('#usernameSelectCreateToDo').trigger('change');
        </script>
        <!-- Preselect Users in EditToDo -->
        <script>
            <c:forEach items="${userstodo}" var="user">
            $('#usernameSelectEditToDo').val('${user}');
            $('#usernameSelectEditToDo').trigger('change');
            </c:forEach>
        </script>
    </body>
</html>

