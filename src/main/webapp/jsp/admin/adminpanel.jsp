<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
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
                    <form class="needs-validation" method="POST" action="controller" autocomplete="off"
                          onsubmit="return cryptChange()" novalidate>
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label for="passwordChange">Сменить пароль</label>
                                    <input id="passwordChange" type="password" class="form-control" required
                                           minlength="6" maxlength="50">
                                    <input type="hidden" id="user-real-password-change" name="newPassword"/>
                                    <input type="hidden" name="command" value="changePassword"/>
                                    <div class="auth_error"> ${errorMessage}</div>
                                    <div class="valid-feedback">Выглядит нормально</div>
                                    <div class="invalid-feedback">Введите корректный пароль (минимум 6 символов)</div>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="button_success button_width_30">
                            Сменить
                        </button>
                        <hr>
                    </form>
                </div>
                <div class="container sidebar-block_border">
                    <label for="avatarUpload">Сменить аватар</label>
                    <form id="avatarUpload" class="md-form" action="upload" method="POST" enctype="multipart/form-data" >
                        <input type="hidden" name="uploadType" value="avatar"/>
                        <div class="file-field">
                            <input type="file" name="file" required="required" accept="image/*">
                        </div>
                        <hr>
                        <div class="auth_field">
                            <div class="auth_error"> ${uploadFileMessage}</div>
                        </div>
                        <button type="submit" class="button_success button_width_30">
                            Загрузить
                        </button>
                        <hr>
                    </form>
                </div>
                <div class="container sidebar-block_border">
                    <label for="beerSubmit">Добавить тип пива</label>
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
                                               placeholder="Тип пива"
                                               aria-label="Тип пива"
                                               aria-describedby="basic-addon1">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="button_success button_width_30">
                            Добавить
                        </button>
                    </form>
                </div>
                <div class="auth_field">
                    <div class="auth_error"> ${foodSubmitMessage}</div>
                </div>
                <div class="container sidebar-block_border">
                    <label for="foodSubmit">Добавить тип кухни</label>
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
                                               placeholder="Тип кухни"
                                               aria-label="Тип кухни"
                                               aria-describedby="basic-addon1">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="button_success button_width_30">
                            Добавить
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
