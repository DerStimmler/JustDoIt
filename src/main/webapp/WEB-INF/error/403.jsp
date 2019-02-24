<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="base_url" value="<%=request.getContextPath()%>" />

<!DOCTYPE html>
<html>
    <head>
        <title>JustDoIt | 403</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/403.css"/>">
    </head>
    <body>
        <div class="scene">
            <div class="overlay"></div>
            <div class="overlay"></div>
            <div class="overlay"></div>
            <div class="overlay"></div>
            <span class="bg-403">403</span>
            <div class="text">
                <span class="hero-text"></span>
                <span class="msg">can't let <span>you</span> in.</span>
                <span class="support">
                    <span>No permission to access.</span>
                    <a href="<c:url value="/"/>">Go to Homepage</a>
                </span>
            </div>
            <div class="lock"></div>
        </div>
    </body>
</html>
