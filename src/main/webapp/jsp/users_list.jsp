<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Goods</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/w3.css" />
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <div class="w3-container">
        <button onclick="document.getElementById('id01').style.display='block'" class="w3-button w3-black"><fmt:message key="goods.new_goods"/></button>

        <div id="id01" class="w3-modal">
            <div class="w3-modal-content">
                <div class="w3-container">
                    <span onclick="document.getElementById('id01').style.display='none'" class="w3-button w3-display-topright">&times;</span>
                    <p>Some text. Some text. Some text.</p>
                    <form method="post" action="controller">
                        <input type="hidden" name="action" value="user_create">
                        <label for="goods_name"><fmt:message key="goods.creation.name"/></label>
                        <input type="text" required id="goods_name" name="goods_name"><br>
                        <label for="goods_scancode"><fmt:message key="goods.creation.scancode"/></label>
                        <input type="text" required id="goods_scancode" name="goods_scancode"><br>
                        <label for="goods_weight"><fmt:message key="goods.creation.weight"/></label>
                        <input type="checkbox" required id="goods_weight" name="goods_weight"><br>
                        <label for="goods_price"><fmt:message key="goods.creation.price"/></label>
                        <input type="number" required id="goods_price" name="goods_price" step="0.01" min="0" max="100000">
                        <input class="button" type="submit" value=<fmt:message key="goods.creation.submit"/>>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<table>
    <tr>
        <th><fmt:message key="users.table.login"/></th>
        <th><fmt:message key="users.table.role"/></th>
    </tr>
    <tbody>
    <c:forEach var="user" items="${userlist}">
        <tr>
            <td>${user.login}</td>
            <td>${user.role}</td>

     <%--       <td>${goods.amount}</td> --%>

        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${currentPage != 1}">
    <td><a href="controller?action=user_list&page=${currentPage - 1}"><fmt:message key="table.previous"/></a></td>
</c:if>


<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="controller?action=user_list&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<c:if test="${currentPage lt noOfPages}">
    <td><a href="controller?action=user_list&page=${currentPage + 1}"><fmt:message key="table.next"/></a></td>
</c:if>
</body>
</html>
