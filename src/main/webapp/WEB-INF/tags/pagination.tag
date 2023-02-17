<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="action_page" type="java.lang.String" required="true" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<div style="position: fixed; bottom: 5px; right: 10px;">
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:if test="${currentPage != 1}">
            <td><a href="controller?action=${action_page}&page=${currentPage - 1}"><fmt:message key="table.previous"/></a></td>
        </c:if>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="controller?action=${action_page}&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt noOfPages}">
            <td><a href="controller?action=${action_page}&page=${currentPage + 1}"><fmt:message key="table.next"/></a></td>
        </c:if>
    </tr>
</table>
</div>