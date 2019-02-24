<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="base_url" value="<%=request.getContextPath()%>" />

<!DOCTYPE html>
<html>
    <head>
        <title>JustDoIt | 404</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/404.css"/>">
    </head>
    <body>
        <div class="glitch">
            <span class="info"></span>
        </div>

        <div class="code-area">
            <span style="color: #777;font-style:italic;">
                // 404 page not found.
            </span>
            <span>
                <span style="color:#d65562;">
                    if
                </span>
                (<span style="color:#4ca8ef;">!</span><span style="font-style: italic;color:#bdbdbd;">found</span>)
                {
            </span>
            <span>
                <span style="padding-left: 15px;color:#2796ec">
                    <i style="width: 10px;display:inline-block"></i>throw
                </span>
                <span>
                    (<span style="color: #a6a61f">"(╯°□°)╯︵ ┻━┻"</span>);
                </span>
                <span style="display:block">}</span>
                <span style="color: #777;font-style:italic;">
                    // <a href="<c:url value="/"/>">Go home!</a>
                </span>
            </span>
        </div>
    </body>
</html>