<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<label for="avatarUpload"><fmt:message key="change.avatar.label"/></label>
<form id="avatarUpload" class="md-form" action="upload" method="POST" enctype="multipart/form-data" >
    <input type="hidden" name="uploadType" value="avatar" accept="image/*"/>
    <div class="file-field">
        <input type="file" name="file" required="required">
    </div>
    <hr>
    <div class="auth_field">
        <div class="auth_error"> ${uploadFileMessage}</div>
    </div>
    <button type="submit" class="button_success button_width_30">
        <fmt:message key="change.avatar.button"/>
    </button>
    <hr>
</form>
</body>
</html>
