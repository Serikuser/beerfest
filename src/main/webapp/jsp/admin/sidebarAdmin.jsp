<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
</head>
<body>
<div class="sidebar-block_header">
</div>
<div class="sidebar-block_content profile-info">
    <div class="profile-info_item">
        <form action="controller" class="needs-validation" onsubmit="return cryptChange()" novalidate>
            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <input type="hidden" name="command" value="select_users"/>
                    </div>
                </div>
            </div>
            <button type="submit" class="button_success button_width_100"><fmt:message key="panel.admin.button.users"/></button>
        </form>
    </div>
    <div class="profile-info_item">
        <form action="controller" class="needs-validation" novalidate>
            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <input type="hidden" name="command" value="etc"/>
                    </div>
                </div>
            </div>
            <button type="submit" class="button_success button_width_100"><fmt:message key="panel.admin.button.addnews"/></button>
        </form>
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
