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

<table>
    <tr>
        <th><fmt:message key="checkout.table.date"/></th>
        <th><fmt:message key="checkout.table.user"/></th>
        <th><fmt:message key="checkout.table.warehouse"/></th>
        <th><fmt:message key="checkout.table.closed"/></th>
    </tr>
    <tbody>
    <c:forEach var="entity" items="${checkout_shift_list}">
        <tr>
            <td><fmt:formatDate value="${entity.date}" type="both"/></td>
            <td>${entity.user}</td>
            <td>${entity.warehouse}</td>
            <td><ctg:boolean_format check="${entity.closed}"/></td>
            <td>
                <form method="post" action="controller">
                    <input type="hidden" name="action" value="checkout_open">
                    <input type="hidden"  name="checkout_id" value="${entity.id}">
                    <input class="button" type="submit" value=<fmt:message key="checkout.open"/>>
                </form>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>

<ctgf:pagination action_page="checkout_list"></ctgf:pagination>

</body>
</html>