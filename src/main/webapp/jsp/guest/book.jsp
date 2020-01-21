<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                        <div class="auth_error"> ${bookErrorMessage}</div>
                        <c:forEach items="${bookList}" var="book">
                            <form action="controller" class="needs-validation">
                            <article class="news news_main">
                                <div class="container">
                                        <input type="hidden" name="command" value="book_delete"/>
                                        <input type="hidden" name="bookId" value="${book.id}"/>
                                        <h1><i class="fas fa-utensils"></i> ${book.barName}</h1>
                                        <hr>
                                        <i class="fas fa-chair"></i>${book.places}
                                        <i class="fas fa-clock"></i>${book.date}
                                        <hr>
                                </div>
                                <button type="submit" class="button_success button_width_30"><fmt:message
                                        key="panel.guest.button.delete"/></button>
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
