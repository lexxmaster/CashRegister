<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="action" required="true"
              type="java.lang.String" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<c:if test="${2 != 1}">
    <td><a href="controller?action=${action}&page=1"><fmt:message key="table.previous"/></a></td>
</c:if>


<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="4" var="i">
            <c:choose>
                <c:when test="${2 eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="controller?action=${action}&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<c:if test="${2 lt 4}">
    <td><a href="controller?action=${action}&page=3"><fmt:message key="table.next"/></a></td>
</c:if>
</body>
</html>