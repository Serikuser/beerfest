<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<head>

</head>
<body>
<form class="needs-validation" method="POST" action="controller" autocomplete="off"
      onsubmit="return cryptChange()" novalidate>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <label for="passwordChange"><fmt:message key="change.password.label"/></label>
                <input id="passwordChange" type="password" class="form-control" required
                       minlength="6" maxlength="50">
                <input type="hidden" id="user-real-password-change" name="newPassword"/>
                <input type="hidden" name="command" value="change_password"/>
                <div class="auth_error"> ${errorMessage}</div>
                <div class="valid-feedback"><fmt:message key="change.password.valid"/></div>
                <div class="invalid-feedback"><fmt:message key="change.password.invalid"/></div>
            </div>
        </div>
    </div>
    <button type="submit" class="button_success button_width_30">
        <fmt:message key="change.password.button"/>
    </button>
</form>
</body>
</html>
