<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored = "false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<header>
    <table>
        <tr>
            <c:if test="${empty checkout_shift}">
                <th>
                    <form method="get" action="controller">
                        <input type="hidden" name="action" value="checkout_create">
                        <input class="button" type="submit" value=<fmt:message key="cashier_actions.checkout_create"/>>
                    </form>
                </th>
            </c:if>
            <c:if test="${not empty checkout_shift}">
                <th>
                    <form method="get" action="controller">
                        <input type="hidden" name="action" value="checkout_close">
                        <input class="button" type="submit" value=<fmt:message key="cashier_actions.checkout_close"/>>
                    </form>
                </th>
            </c:if>

            <th>
                <form method="get" action="controller">
                    <input type="hidden" name="action" value="create_order">
                    <input class="button" type="submit" value=<fmt:message key="cashier_actions.order_create"/>>
                </form>
            </th>
        </tr>
    </table>



    </div>

</header>
