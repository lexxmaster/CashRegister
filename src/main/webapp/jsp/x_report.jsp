<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>X-Report</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>
    <b><fmt:message key="x_report.head"/></b><br><br>
</h1>
<h2>
    <b><fmt:message key="x_report.sell"/></b><br>
</h2>
<div>
    <b><fmt:message key="x_report.check_amount"/> ${report.checkAmount}</b><br>
    <b><fmt:message key="x_report.total_sum"/> ${report.sum}</b><br><br>
    <b><fmt:message key="x_report.cashier"/> ${report.user}</b><br>
    <b><fmt:message key="x_report.date"/> <fmt:formatDate value="${report.checkoutShift.date}"/></b><br>
</div>
<div>
    <form method="get" action="controller">
        <input type="hidden" name="action" value="order_list">
        <input class="button" type="submit" value=<fmt:message key="common.close"/>>
    </form>
</div>
</body>
</html>
