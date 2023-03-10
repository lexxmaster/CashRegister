<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored = "false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/w3.css" />
</head>
<header>
    <div>
        <table>
            <tr>
                <th>
                    <form method="post" action="controller">
                        <input type="hidden" name="action" value="lang">
                        <input type="hidden" name="lang" value="en">
                        <input type="image" src="img/en.webp" alt="English" />
                    </form>
                </th>
                <th>
                    <form method="post" action="controller">
                        <input type="hidden" name="action" value="lang">
                        <input type="hidden" name="lang" value="uk">
                        <input type="image" src="img/uk.webp" alt="Ukraine" />
                    </form>
                </th>
                <th>
                    <form method="post" action="controller">
                        <input type="hidden" name="action" value="logout">
                        <input class="button" type="submit" value=<fmt:message key="login.logout"/>>
                    </form>
                </th>

            </tr>
        </table>
    </div>
</header>
