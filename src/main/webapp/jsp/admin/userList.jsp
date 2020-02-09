<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Beer Festival News</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.css"
          rel="stylesheet">
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.js"></script>

</head>
<body>
<div class="app">
    <jsp:include page="/jsp/header.jsp"/>
    <div class="app_inner" style="padding-top: 64px">
        <div class="main">
            <div class="main_inner">
                <div class="feed">
                    <div class="auth_field">
                        <div class="auth_error"> ${userListMessage}</div>
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <c:forEach begin="1" end="${maxValue}" varStatus="loop">
                                    <form class="page-item" id="update" action="controller" method="POST">
                                        <input type="hidden" name="pageNumber" value="${loop.count-1}"/>
                                        <input type="hidden" name="command" value="select_users"/>
                                            <button type="submit" class="page-link button_success">${loop.count}</button>
                                    </form>
                                </c:forEach>
                            </ul>
                        </nav>
                        <c:forEach items="${userList}" var="user">
                            <form action="controller" class="needs-validation">
                                <article class="news news_main">
                                    <div class="container">
                                        <input type="hidden" name="userId" value="${user.id}"/>
                                        <h3><i class="fas fa-address-card"></i> <c:out value="${user.login}"/></h3>
                                        <hr>
                                        <i class="fas fa-thermometer"></i> ${user.status}
                                       <ctg:role role="${user.role}"/>
                                        <i class="fas fa-at"></i>${user.email}
                                        <hr>
                                    </div>
                                </article>
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="sidebar">
            <jsp:include page="/jsp/sidebar.jsp"/>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>
<script src="https://kit.fontawesome.com/e9321a3cf3.js"></script>
<script src="<c:url value="/js/auth_form.js" />"></script>
<script src="<c:url value="/js/validation.js" />"></script>
<script src="<c:url value="/js/header.js" />"></script>

</body>
</html>
