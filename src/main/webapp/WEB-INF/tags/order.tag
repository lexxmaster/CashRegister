<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="order_object" required="true"
              type="com.cashregister.model.entity.Order " %>

<%@ taglib prefix="ctg"  uri="customtags"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<c:if test="${order_object ne null}">
    <tr>
        <td><fmt:formatDate value="${order_object.date}" type="both"/></td>
        <td>${order_object.warehouse}</td>
        <td>${order_object.total}</td>
        <td>${order_object.user.login}</td>
        <td><ctg:boolean_format check="${order.closed}"/></td>
        <td>
            <form method="get" action="controller">
                <input type="hidden" name="action" value="order_open">
                <input type="hidden" name="order_id" value="${order_object.id}">
                <input class="button" type="submit" value=<fmt:message key="orders.table.edit"/>>
            </form>
        </td>
        <td>
            <form method="get" action="controller">
                <input type="hidden" name="action" value="order_delete">
                <input type="hidden" name="order_id" value="${order_object.id}">
                <input class="button" type="submit" value=<fmt:message key="orders.table.delete"/>>
            </form>
        </td>
    </tr>
</c:if>