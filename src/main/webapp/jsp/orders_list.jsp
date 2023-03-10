<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg"  uri="customtags"%>
<%@ taglib prefix="ctgf" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored = "false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Orders</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/w3.css" />
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
<ctgf:head/>
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

<ctgf:pagination action_page="order_list"></ctgf:pagination>

</body>
</html>

