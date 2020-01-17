<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error 500 (Internal Server Error)</title>
    <link href="<c:url value="/css/error.css" />" rel="stylesheet">
</head>
<body>
HTTP Status 500 – Internal Server Error

<%= exception %>
</body>
</html>