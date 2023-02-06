<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg"  uri="customtags"%>
<%@ taglib prefix="cust_tag" tagdir="/WEB-INF/tags" %>

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
            <cust_tag:order order_object="${order}"></cust_tag:order>
        </c:forEach>
    </tbody>
</table>
</body>
</html>

