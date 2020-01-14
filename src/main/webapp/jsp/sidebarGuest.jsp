<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
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
                <div class="auth_header">Авторизация</div>
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
                                           placeholder="Логин" tabindex="0">
                                    <div class="valid-feedback">Логин выглядит нормально.</div>
                                    <div class="invalid-feedback">Введите логин.</div>
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
                                           autocomplete="password" placeholder="Пароль" tabindex="0"
                                           id="passwordLogin">
                                    <input type="hidden" id="user-real-password-login" name="password"/>
                                    <div class="valid-feedback">Пароль выглядит нормально.</div>
                                    <div class="invalid-feedback">Введите пароль.</div>
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
                                <span class="button_title">Войти</span>
                            </button>
                        </div>
                        <div class="auth_field">
                            <div class="auth_error"> ${errorMessage}</div>
                        </div>
                        <div class="auth_field">
                            <a class="auth_action"
                               id="register-form-link">Регистрация</a>
                        </div>
                    </form>
                </div>
            </div>
            <div class="tabs_tab auth" style="display: none" id="register-form">
                <div class="auth_header">Регистрация</div>
                <div class="auth_content">
                    <form class="needs-validation" method="POST" action="controller" autocomplete="off"
                          onsubmit="return cryptRegister()" novalidate>
                        <input type="hidden" name="command" value="signup">
                        <div class="auth_field">
                            <div class="input input_small">
                                <span class="input_box">
                                <input class="input_input form-control" type="text" autocomplete="on"
                                       name="username" required minlength="4" maxlength="16"
                                       placeholder="Логин">
                                    <div class="valid-feedback">Логин выглядит нормально.</div>
                                    <div class="invalid-feedback">Введите логин.</div>
                                </span>
                            </div>


                        </div>
                        <div class="auth_field">
                            <div class="input input_small">
                                  <span class="input_box">
                                <input class="input_input form-control" type="email"
                                       required minlength="6" maxlength="160"
                                       autocomplete="on" name="email" placeholder="E-mail">
                                      <div class="valid-feedback">E-mail выглядит нормально.</div>
                                      <div class="invalid-feedback">Введите корректный E-mail.</div>
                                  </span>
                            </div>

                        </div>
                        <div class="auth_field">
                            <div class="input input_box input_small" data-role="auth">
												<span class="input_box">
													<input class="input_input form-control" type="password"
                                                           id="passwordRegister"
                                                           autocomplete="new-password"
                                                           placeholder="Пароль" required minlength="6" maxlength="50">
                                                    <input type="hidden" id="user-real-password-register"
                                                           name="password"/>
                                                    <div class="valid-feedback">Пароль выглядит нормально.</div>
                                                    <div class="invalid-feedback">Введите пароль. (Минимум 6 символов)</div>
												</span>

                            </div>
                        </div>
                        <div class="auth_field">
                            <div class="input input_small">
                                <select class="input_input" style="width: 100%;" name="role">
                                    <option selected="selected" value="guest" type="text">Гость</option>
                                    <option value="participant" type="text">Участник</option>
                                </select>
                            </div>
                        </div>
                        <div class="auth__field_terms">
                            Создавая аккаунт, я соглашаюсь с <a href="/information/rules"
                                                                target="_blank">правилами
                            Фестиваля</a>
                            и даю согласие на <a href="/information/terms" target="_blank">обработку
                            персональных данных</a>.
                        </div>
                        <div class="auth_field">
                            <span class="auth_error"></span>
                        </div>
                        <div class="auth_field">
                            <button type="submit" class="button_success button_width_100">
                                Создать аккаунт
                            </button>
                        </div>
                        <div class="auth_field">
                            <a class="auth_action"
                               id="login-form-link">Авторизация</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="sidebar-block sidebar-block_border">
    <div class="sidebar-block_header">
        <h4>На других языках</h4>
    </div>
    <div class="sidebar-block_content">
        <div>
            <img src="img/lang/ru.jpg" class="lang_img"> Русский язык
        </div>
        <div>
            <img src="img/lang/by.jpg" class="lang_img"> Беларуская мова
        </div>
        <div>
            <img src="img/lang/en.jpg" class="lang_img">English language
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
