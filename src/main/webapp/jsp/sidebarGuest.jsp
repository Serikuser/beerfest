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
<div class="sidebar-block sidebar-block_border">
    <div class="tabs sidebar-auth">
        <div class="tabs_container">
            <div class="tabs_tab tabs_tab_visible auth" id="login-form">
                <div class="auth_header"><fmt:message key="sidebar.guest.label.login"/></div>
                <div class="auth_content">
                    <form class="needs-validation" method="POST" action="controller"
                          onsubmit="return cryptLogin()" novalidate>
                        <input type="hidden" name="command" value="login">
                        <div class="auth_field">
                            <div class="input input_small">
                                <div class="input_box">
                                    <input class="input_input form-control" type="text" name="username"
                                           autocapitalize="none" autocorrect="off"
                                           autocomplete="username" required minlength="3" maxlength="40"
                                           placeholder="<fmt:message key="sidebar.guest.placeholder.login"/>" tabindex="0">
                                    <div class="valid-feedback"><fmt:message key="sidebar.guest.login.valid"/></div>
                                    <div class="invalid-feedback"><fmt:message key="sidebar.guest.login.invalid"/></div>
                                    <span class="input_clear input_clear_visible">
                                                        <span class="input_clear input_clear_visible">
                                                            <span class="input_clear-inner"><span
                                                                    class="input_clear-background"></span></span>
                                                        </span>
                                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="auth_field">
                            <div class="input input_small">
                                <div class="input_box">
                                    <input class="input_input form-control" type="password" required
                                           autocomplete="password" placeholder="<fmt:message key="sidebar.guest.placeholder.password"/>" tabindex="0"
                                           id="passwordLogin">
                                    <input type="hidden" id="user-real-password-login" name="password"/>
                                    <div class="valid-feedback"><fmt:message key="sidebar.guest.password.valid"/></div>
                                    <div class="invalid-feedback"><fmt:message key="sidebar.guest.password.invalid"/></div>
                                    <span class="input_clear input_clear_visible">
                                                        <span class="input_clear input_clear_visible">
                                                            <span class="input_clear-inner"><span
                                                                    class="input_clear-background"></span></span>
                                                        </span>
                                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="auth_field">
                            <span class="auth_error"></span>
                        </div>
                        <div class="auth_field">
                            <button type="submit" class="button_success button_width_100">
                                <span class="button_title"><fmt:message key="sidebar.guest.button.login"/></span>
                            </button>
                        </div>
                        <div class="auth_field">
                            <div class="auth_error"> ${errorMessage}</div>
                        </div>
                        <div class="auth_field">
                            <a class="auth_action"
                               id="register-form-link"><fmt:message key="sidebar.guest.label.register"/></a>
                        </div>
                    </form>
                </div>
            </div>
            <div class="tabs_tab auth" style="display: none" id="register-form">
                <div class="auth_header"><fmt:message key="sidebar.guest.label.login"/></div>
                <div class="auth_content">
                    <form class="needs-validation" method="POST" action="controller" autocomplete="off"
                          onsubmit="return cryptRegister()" novalidate>
                        <input type="hidden" name="command" value="signup">
                        <div class="auth_field">
                            <div class="input input_small">
                                <span class="input_box">
                                <input class="input_input form-control" type="text" autocomplete="on"
                                       name="username" required minlength="4" maxlength="16"
                                       placeholder="<fmt:message key="sidebar.guest.placeholder.login"/>">
                                    <div class="valid-feedback"><fmt:message key="sidebar.guest.registration.login.valid"/></div>
                                    <div class="invalid-feedback"><fmt:message key="sidebar.guest.registration.login.invalid"/></div>
                                </span>
                            </div>


                        </div>
                        <div class="auth_field">
                            <div class="input input_small">
                                  <span class="input_box">
                                <input class="input_input form-control" type="email"
                                       required minlength="6" maxlength="160"
                                       autocomplete="on" name="email" placeholder="<fmt:message key="sidebar.guest.placeholder.email"/>">
                                      <div class="valid-feedback"><fmt:message key="sidebar.guest.registration.email.valid"/></div>
                                      <div class="invalid-feedback"><fmt:message key="sidebar.guest.registration.email.invalid"/></div>
                                  </span>
                            </div>

                        </div>
                        <div class="auth_field">
                            <div class="input input_box input_small" data-role="auth">
												<span class="input_box">
													<input class="input_input form-control" type="password"
                                                           id="passwordRegister"
                                                           autocomplete="new-password"
                                                           placeholder="<fmt:message key="sidebar.guest.placeholder.password"/>" required minlength="6" maxlength="50">
                                                    <input type="hidden" id="user-real-password-register"
                                                           name="password"/>
                                                    <div class="valid-feedback"><fmt:message key="sidebar.guest.registration.password.valid"/></div>
                                                    <div class="invalid-feedback"><fmt:message key="sidebar.guest.registration.password.invalid"/></div>
												</span>

                            </div>
                        </div>
                        <div class="auth_field">
                            <div class="input input_small">
                                <select class="input_input" style="width: 100%;" name="role">
                                    <option selected="selected" value="guest" type="text"><fmt:message key="sidebar.guest.registration.optional.guest"/></option>
                                    <option value="participant" type="text"><fmt:message key="sidebar.guest.registration.optional.participant"/></option>
                                </select>
                            </div>
                        </div>
                        <div class="auth__field_terms">
                            <fmt:message key="sidebar.guest.registration.rules"/>
                        </div>
                        <div class="auth_field">
                            <span class="auth_error"></span>
                        </div>
                        <div class="auth_field">
                            <button type="submit" class="button_success button_width_100">
                                <fmt:message key="sidebar.guest.registration.button.register"/>
                            </button>
                        </div>
                        <div class="auth_field">
                            <a class="auth_action"
                               id="login-form-link"><fmt:message key="sidebar.guest.registration.label.login"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>
<script src="<c:url value="/js/auth_form.js" />"></script>
<script src="<c:url value="/js/validation.js" />"></script>
<script src="<c:url value="/js/header.js" />"></script>
</html>
