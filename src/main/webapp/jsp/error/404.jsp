<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Error 404 (Not Found)!!1</title>
    <link href="<c:url value="/css/error.css" />" rel="stylesheet">
</head>
<body>
<div class="logo">
    <a href="/Beerfest"> <img src="/Beerfest/img/logo.png" class="logo_img"></a>
</div>
<p><b>404.</b>
    <ins>Упс...</ins>
</p>
<p><fmt:message key="error.404.text"/></p>
</p>
</body>
</html>