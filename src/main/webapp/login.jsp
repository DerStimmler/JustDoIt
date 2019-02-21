<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Login</title>
    </head>
    <body>
            <h1>Login</h1>
            <form method="POST">
                <input type="text" name="name" placeholder="Name" value="${login_form.name}"/>
                <input type="text" name="password" placeholder="Password" value="${login_form.password}"/>
                <input type="checkbox" name="rememberMe" value="true" <c:if test="${login_form.rememberMe eq true}">checked</c:if>>
                <button type="submit">Login</button>
            </form>

            <c:if test="${!empty login_form.errors}">
                <p>
                    <c:forEach items="${login_form.errors}" var="error">
                        ${error}<br>
                    </c:forEach>
                </p>
            </c:if>
    </body>
</html>

