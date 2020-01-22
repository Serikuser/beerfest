<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
</head>
<body>
<aside class="sidebar_inner" style="width: 277.796px; margin-top: 0px; display: ${displayGuest}">
    <jsp:include page="/jsp/sidebarGuest.jsp"/>
</aside>
<aside class="sidebar_inner" style="width: 277.796px; margin-top: 0px;
        display: <c:if test="${displayUser == null}">none</c:if>">
    <div class="sidebar-block sidebar-block_border">
        <jsp:include page="/jsp/sidebarUser.jsp"/>
    </div>
</aside>
<aside class="sidebar_inner" style="width: 277.796px; margin-top: 0px;">
    <jsp:include page="/jsp/sidebarLanguage.jsp"/>
</aside>

<script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>
<script src="<c:url value="/js/auth_form.js" />"></script>
<script src="<c:url value="/js/validation.js" />"></script>
<script src="<c:url value="/js/header.js" />"></script>
</body>
</html>
