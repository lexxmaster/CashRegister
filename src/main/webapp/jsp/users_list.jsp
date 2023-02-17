<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg"  uri="customtags"%>
<%@ taglib prefix="ctgf" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Goods</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/w3.css" />
</head>
<body>
<ctgf:head/>
<form method="get" action="controller">
    <input type="hidden" name="action" value="user_create">
    <input class="button" type="submit" value=<fmt:message key="user.create"/>>
</form><br>
<table>
    <tr>
        <th><fmt:message key="users.table.login"/></th>
        <th><fmt:message key="users.table.role"/></th>
    </tr>
    <tbody>
    <c:forEach var="user" items="${userlist}">
        <tr>
            <td>${user.login}</td>
            <td>${user.role}</td>
            <td><form method="get" action="controller">
                <input type="hidden" name="action" value="user_open">
                <input type="hidden"  name="user_id" value="${user.id}"><br><br>
                <input class="button" type="submit" value=<fmt:message key="users.table.open"/>>
            </form></td>

     <%--       <td>${goods.amount}</td> --%>

        </tr>
    </c:forEach>
    </tbody>
</table>

<ctgf:pagination action_page="user_list"></ctgf:pagination>

</body>
</html>
