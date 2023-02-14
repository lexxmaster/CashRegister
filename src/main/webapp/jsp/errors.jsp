<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>

</head>
<body>
<c:if test="${err_login}">
    <div>
        <strong><fmt:message key="common.error"/></strong> <fmt:message key="error.login"/>
    </div>
</c:if>
<c:if test="${err_login}">
    <div>
        <strong><fmt:message key="common.error"/></strong> <fmt:message key="error.pass"/>
    </div>
</c:if>
</body>
</html>
