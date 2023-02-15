<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="action" required="true"
              type="java.lang.String" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<td><fmt:formatDate value="${entity.date}" type="both"/></td>
<td>${entity.user}</td>
<td>${entity.warehouse}</td>
<td><ctg:boolean_format check="${entity.closed}"/></td>