<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.cashregister.model.entity.Role"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/w3.css" />
</head>
<header>
    <table>
        <tr>
            <c:if test="${empty checkout_shift && role eq Role.CASHIER}">
                <th>
                    <form method="get" action="controller">
                        <input type="hidden" name="action" value="checkout_create">
                        <input class="button" type="submit" value=<fmt:message key="cashier_actions.checkout_create"/>>
                    </form>
                </th>
            </c:if>
            <c:if test="${not empty checkout_shift && role eq Role.SENIOR_CASHIER}">
                <th>
                    <form method="get" action="controller">
                        <input type="hidden" name="action" value="x_report_view">
                        <input class="button" type="submit" value=<fmt:message key="cashier_actions.x_report"/>>
                    </form>
                </th>
                <c:if test="${!checkout_shift.closed}">
                    <th>
                        <form method="get" action="controller">
                            <input type="hidden" name="action" value="z_report_create">
                            <input class="button" type="submit" value=<fmt:message key="cashier_actions.z_report"/>>
                        </form>
                    </th>
                </c:if>
                <th>
                    <form method="get" action="controller">
                        <input type="hidden" name="action" value="checkout_exit">
                        <input class="button" type="submit" value=<fmt:message key="cashier_actions.checkout_close"/>>
                    </form>
                </th>
            </c:if>
            <c:if test="${not empty checkout_shift && role eq Role.CASHIER}">
                <th>
                    <form method="get" action="controller">
                        <input type="hidden" name="action" value="create_order">
                        <input class="button" type="submit" value=<fmt:message key="cashier_actions.order_create"/>>
                    </form>
                </th>
            </c:if>
        </tr>
    </table>



    </div>

</header>
