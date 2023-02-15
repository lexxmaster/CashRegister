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
<%@ include file="header.jsp" %>

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




<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:if test="${currentPage != 1}">
            <td><a href="controller?action=checkout_list&page=${currentPage - 1}"><fmt:message key="table.previous"/></a></td>
        </c:if>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="controller?action=checkout_list&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt noOfPages}">
            <td><a href="controller?action=checkout_list&page=${currentPage + 1}"><fmt:message key="table.next"/></a></td>
        </c:if>
    </tr>
</table>


</body>
</html>