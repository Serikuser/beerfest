<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Beer Festival News</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
</head>
<body>
<div class="app">
    <jsp:include page="/jsp/header.jsp"/>
    <div class="app_inner" style="padding-top: 64px">
        <div class="main">
            <div class="main_inner">
                <div class="feed">
                    <c:forEach items="${feed}" var="feed">
                        <article class="news news_main">
                            <header class="news_header">
                                <h2 class="news_title">${feed.title}</h2>
                            </header>
                            <div class="news_content news_typography ">
                                <div class="news_content-inner">
                                    <div class="news-block_text">
                                        <p>${feed.text}</p>
                                    </div>
                                    <div class="news-block_img">
                                        <img src="${feed.imgSrc}" class="rounded mx-auto d-block"
                                             alt="">
                                    </div>
                                </div>
                            </div>
                        </article>
                    </c:forEach>
            </div>
        </div>
    </div>
        <div class="sidebar">
            <jsp:include page="/jsp/sidebar.jsp"/>
        </div>
</div>
</div>
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
