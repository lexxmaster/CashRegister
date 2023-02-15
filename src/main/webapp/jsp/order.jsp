<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/w3.css" />
</head>
<body>
<%@ include file="header.jsp" %>
<h1><fmt:message key="order.order"/> <fmt:formatDate value="${order.date}"/></h1>
<div>
    <b><fmt:message key="order.user"/> ${order.user}</b>
</div>
<form method="post" action="controller">
    <input type="hidden" name="action" value="order_close">
    <input type="hidden"  name="order_id" value="${order.id}"><br><br>
    <input class="button" type="submit" value=<fmt:message key="order.close"/>>
</form>
<div>
    <div class="w3-container">
        <button onclick="document.getElementById('id01').style.display='block'" class="w3-button w3-black"><fmt:message key="order.add_goods"/></button>

        <div id="id01" class="w3-modal">
            <div class="w3-modal-content">
                <div class="w3-container">
                    <span onclick="document.getElementById('id01').style.display='none'" class="w3-button w3-display-topright">&times;</span>
                    <p><fmt:message key="order.add_goods.header"/></p>
                    <form method="post" action="controller">
                        <input type="hidden" name="action" value="order_goods_add">
                        <input type="text" required name="search_goods"><br><br>
                        <input class="button" type="submit" value=<fmt:message key="order.add_goods.submit"/>>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
    <table>
        <tr>
            <th><fmt:message key="order.table.name"/></th>
            <th><fmt:message key="order.table.amount"/></th>
            <th><fmt:message key="order.table.price"/></th>
            <th><fmt:message key="order.table.total"/></th>
        </tr>
        <tbody>
        <c:forEach var="entity" items="${order.goodsList}">
            <tr>
                <td>${entity.getKey().name}</td>
                <td>
                    <form method="post" action="controller">
                        <input type="hidden" name="action" value="order_goods_amount">
                        <input type="hidden"  name="order_id" value="${order.id}">
                        <input type="hidden"  name="goods_id" value="${entity.getKey().id}">
                        <input type="number" name="goods_set_amount" value="${entity.getValue().amount}" step="0.001" min="0" max="100000">
                        <input class="button" type="submit" value=<fmt:message key="common.apply"/>>
                    </form>
                </td>

                <td>${entity.getValue().price}</td>
                <td>${entity.getValue().total}</td>
                <c:if test="${role eq Role.SENIOR_CASHIER}">
                <td><form method="post" action="controller">
                    <input type="hidden" name="action" value="order_goods_delete">
                    <input type="hidden"  name="order_id" value="${order.id}"><br><br>
                    <input type="hidden"  name="goods_id" value="${entity.getKey().id}"><br><br>
                    <input class="button" type="submit" value=<fmt:message key="order.table.delete"/>>
                </form></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="errors.jsp" %>
</body>
</html>
