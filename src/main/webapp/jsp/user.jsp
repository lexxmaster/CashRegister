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
</head>
<body>
<%@ include file="header.jsp" %>
    <form method="post" action="controller">
        <input type="hidden" name="action" value="user_save">
        <input type="hidden" name="id" value="${user.id}">
        <label for="login"><fmt:message key="user.login"/></label><br>
        <input type="text" required placeholder="login" id="login" name="login" value="${user.login}"><br>
        <label for="password"><fmt:message key="user.password"/></label><br>
        <input type="password" required placeholder="password" id="password" name="password" value="${user.passwd}"><br>
        <label for="role"><fmt:message key="user.role"/></label><br>
        <select id="role" name="role">
            <option <c:if test="${user.role == 1}">selected</c:if> value="1"><fmt:message key="role.cashier"/></option>
            <option <c:if test="${user.role == 2}">selected</c:if> value="2"><fmt:message key="role.senior_cashier"/></option>
            <option <c:if test="${user.role == 3}">selected</c:if> value="3"><fmt:message key="role.commodity_expert"/></option>
            <option <c:if test="${user.role == 4}">selected</c:if> value="4"><fmt:message key="role.admin"/></option>
        </select><br><br>
        <input class="button" type="submit" value=<fmt:message key="user.save"/>>
    </form>
<%@ include file="errors.jsp" %>
</body>
</html>
