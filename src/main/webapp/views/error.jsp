<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true"%>
<html>
<head>
    <title>Error</title>
</head>
<body>

<h3>Error Information Is Missing</h3>

<h3>Error Details</h3>
<ul>
    <c:if test="${statusCode != null}">
        <li><strong>Status Code</strong>: ${statusCode} </li>
    </c:if>

    <c:if test="${exceptionType != null}">
        <li><strong>Exception type</strong>: ${exceptionType} </li>
    </c:if>

    <c:if test="${requestUri != null}">
        <li><strong>Request Uri</strong>: ${requestUri} </li>
    </c:if>

    <c:if test="${message != null}">
        <li><strong>Message</strong>: ${message} </li>
    </c:if>

    <c:if test="${exception != null}">
        <li><strong>Exception</strong>: ${exception} </li>
    </c:if>

    <c:if test="${servletName != null}">
        <li><strong>Servlet name</strong>: ${servletName} </li>
    </c:if>
</ul>
<div>
    Click <a href="javascript:window.history.back()">Back</a>
</div>
</body>
</html>
