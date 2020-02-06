<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
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
                    <jsp:include page="/jsp/changePassword.jsp"/>
                </div>
                <div class="container sidebar-block_border">
                    <jsp:include page="/jsp/changeAvatar.jsp"/>
                </div>
                <div class="container sidebar-block_border">
                    <label for="beerSubmit"><fmt:message key="panel.admin.button.addbeer"/></label>
                    <div class="auth_field">
                        <div class="auth_error"> ${beerSubmitMessage}</div>
                    </div>
                    <form id="beerSubmit" method="POST" action="controller" autocomplete="off">
                        <input type="hidden" name="command" value="submit_Beer"/>
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="beerName">
                                                <i class="fas fa-beer"></i>
                                            </span>
                                        </div>
                                        <input type="text" name="beerName" class="form-control"
                                               placeholder="<fmt:message key="panel.admin.placeholder.beer"/>"
                                               aria-label="<fmt:message key="panel.admin.placeholder.beer"/>"
                                               aria-describedby="basic-addon1" required="required">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="button_success button_width_30">
                            <fmt:message key="panel.admin.button.add"/>
                        </button>
                    </form>
                </div>
                <div class="auth_field">
                    <div class="auth_error"> ${foodSubmitMessage}</div>
                </div>
                <div class="container sidebar-block_border">
                    <label for="foodSubmit"><fmt:message key="panel.admin.button.addfood"/></label>
                    <form id="foodSubmit" method="POST" action="controller" autocomplete="off">
                        <input type="hidden" name="command" value="submit_Food"/>
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="foodName">
                                                <i class="fas fa-hamburger"></i>
                                            </span>
                                        </div>
                                        <input type="text" name="foodName" class="form-control"
                                               placeholder="<fmt:message key="panel.admin.placeholder.food"/>"
                                               aria-label="<fmt:message key="panel.admin.placeholder.food"/>"
                                               aria-describedby="basic-addon1" required="required">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="button_success button_width_30">
                            <fmt:message key="panel.admin.button.add"/>
                        </button>
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
