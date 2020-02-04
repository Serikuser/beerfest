<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Beer Festival</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
</head>
<body>
<div class="app">
    <jsp:include page="/jsp/header.jsp"/>
    <div class="app_inner" style="padding-top: 64px">
        <div class="main">
            <div class="main_inner sidebar-block_border">
                <div class="container sidebar-block_border">
                    <label for="addNews"><fmt:message key="add.news.label"/></label>
                    <div class="auth_field">
                        <div class="auth_error"> ${uploadFileMessage}</div>
                    </div>
                    <form id="addNews" class="md-form" action="upload" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="uploadType" value="feed" accept="image/*"/>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="newsTitle"><i class="fas fa-tag"></i></span>
                            </div>
                            <input type="text" name="newsTitle" class="form-control"
                                   placeholder="<fmt:message key="add.news.title"/>"
                                   aria-label="<fmt:message key="add.news.title"/>"
                                   aria-describedby="basic-addon1" required="required">
                        </div>
                        <label for="newsText"><fmt:message key="add.news.text"/></label>
                        <div class="input-group" id="newsText">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="far fa-file-alt"></i></span>
                            </div>
                            <textarea class="form-control" aria-label="With textarea" name="newsText" required="required"></textarea>
                        </div>
                        <div class="file-field">
                            <input type="file" name="file" required="required">
                        </div>
                        <hr>
                        <button type="submit" class="button_success button_width_30">
                            <fmt:message key="add.news.button"/>
                        </button>
                        <hr>
                    </form>
                </div>
            </div>
        </div>
        <div class="sidebar">
            <aside class="sidebar_inner" style="width: 277.796px; margin-top: 0px;">
                <div class="sidebar-block sidebar-block_border">
                    <jsp:include page="/jsp/sidebarUser.jsp"/>
                    <jsp:include page="/jsp/admin/sidebarAdmin.jsp"/>
                </div>
            </aside>
        </div>
    </div>
</div>
<script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>
<script src="https://kit.fontawesome.com/e9321a3cf3.js"></script>
<script src="<c:url value="/js/auth_form.js" />"></script>
<script src="<c:url value="/js/validation.js" />"></script>
<script src="<c:url value="/js/header.js" />"></script>
</body>
</html>
