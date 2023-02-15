<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg"  uri="customtags"%>
<%@ taglib prefix="ctgf" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Orders</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="cashier_actions.jsp" %>
<table>
    <tr>
        <th><fmt:message key="orders.table.date"/></th>
        <th><fmt:message key="orders.table.warehouse"/></th>
        <th><fmt:message key="orders.table.total"/></th>
        <th><fmt:message key="orders.table.user"/></th>
        <th><fmt:message key="orders.table.closed"/></th>
    </tr>
    <tbody>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td><fmt:formatDate value="${order.date}" type="both"/></td>
                <td>${order.warehouse}</td>
                <td>${order.total}</td>
                <td>${order.user.login}</td>
                <td><ctg:boolean_format check="${order.closed}"/></td>
                <td>
                    <form method="get" action="controller">
                        <input type="hidden" name="action" value="order_open">
                        <input type="hidden" name="order_id" value="${order.id}">
                        <input class="button" type="submit" value=<fmt:message key="orders.table.edit"/>>
                    </form>
                </td>
                <c:if test="${role eq Role.SENIOR_CASHIER}">
                <td>
                    <form method="get" action="controller">
                        <input type="hidden" name="action" value="order_delete">
                        <input type="hidden" name="order_id" value="${order.id}">
                        <input class="button" type="submit" value=<fmt:message key="orders.table.delete"/>>
                    </form>
                </td>
                </c:if>
            </tr>

        </c:forEach>
    </tbody>
</table>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:if test="${currentPage != 1}">
            <td><a href="controller?action=order_list&page=${currentPage - 1}"><fmt:message key="table.previous"/></a></td>
        </c:if>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="controller?action=order_list&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt noOfPages}">
            <td><a href="controller?action=order_list&page=${currentPage + 1}"><fmt:message key="table.next"/></a></td>
        </c:if>
    </tr>
</table>


</body>
</html>

