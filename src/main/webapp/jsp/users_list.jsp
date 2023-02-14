<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Goods</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/w3.css" />
</head>
<body>
<%@ include file="header.jsp" %>
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




<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:if test="${currentPage != 1}">
            <td><a href="controller?action=user_list&page=${currentPage - 1}"><fmt:message key="table.previous"/></a></td>
        </c:if>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="controller?action=user_list&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt noOfPages}">
            <td><a href="controller?action=user_list&page=${currentPage + 1}"><fmt:message key="table.next"/></a></td>
        </c:if>
    </tr>
</table>


</body>
</html>
