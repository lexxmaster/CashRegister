<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored = "false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/w3.css" />

    <style>
        body {width: 500px;
            display: flex;
            justify-content: center;}
    </style>
</head>
<body>
    <form method="post" action="controller">
        <input type="hidden" name="action" value="login">
        <label for="login"><fmt:message key="common.login"/></label><br>
        <input type="text" required id="login" placeholder="login" name="login"><br>
        <label for="password"><fmt:message key="common.password"/></label><br>
        <input type="password" id="password" required placeholder="password" name="password"><br><br>
        <input class="button" type="submit" value=<fmt:message key="login.enter"/>>
    </form>
</body>
</html>
