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
                    </form>
                </div>
                <div class="container sidebar-block_border">
                    <label for="avatarUpload">Сменить аватар</label>
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
                            Загрузить
                        </button>
                        <hr>
                    </form>
                </div>
                <div class="container sidebar-block_border">
                    <label for="barSubmit">Заполните форму, что бы принять участие в фестивале</label>
                    <div class="auth_field">
                        <div class="auth_error"> ${baRErrorMessage}</div>
                    </div>
                    <form id="barSubmit" method="POST" action="controller" autocomplete="off">
                        <input type="hidden" name="command" value="submitBar"/>
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="barName">
                                                <i class="fas fa-tag"></i>
                                            </span>
                                        </div>
                                        <input type="text" name="barName" class="form-control"
                                               placeholder="Название бара"
                                               aria-label="Название бара"
                                               aria-describedby="basic-addon1">
                                    </div>
                                    <div class="input-group mb-3">
                                        <select class="custom-select" id="beerTypeSelection" name="beerType">
                                           <c:forEach items="${beerMap}" var="beer">
                                            <option value=${beer.key}>${beer.value}</option>
                                           </c:forEach>
                                        </select>
                                        <div class="input-group-append">
                                            <label class="input-group-text" for="beerTypeSelection">
                                                <i class="fas fa-beer"></i></label>
                                        </div>
                                    </div>
                                    <div class="input-group mb-3">
                                        <select class="custom-select" id="foodTypeSelection" name="foodType">
                                            <c:forEach items="${foodMap}" var="food">
                                                <option value=${food.key}>${food.value}</option>
                                            </c:forEach>
                                        </select>
                                        <div class="input-group-append">
                                            <label class="input-group-text" for="foodTypeSelection">
                                                <i class="fas fa-hamburger"></i></label>
                                        </div>
                                    </div>

                                    <div class="input-group mb-3">
                                        <input type="text" name="places" class="form-control"
                                               placeholder="Количество мест"
                                               aria-label="Recipient's username" aria-describedby="basic-addon2">
                                        <div class="input-group-append">
                                            <span class="input-group-text" id="basic-addon2">
                                                <i class="fas fa-utensils"></i></span>
                                        </div>
                                    </div>
                                    <label for="barDescription">Описание вашего бара</label>
                                    <div class="input-group" id="barDescription">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="far fa-file-alt"></i>
                                            </span>
                                        </div>
                                        <textarea class="form-control" aria-label="With textarea"
                                                  name="barDescription">

                                        </textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="button_success button_width_30">
                            Принять участие
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <div class="sidebar">
            <aside class="sidebar_inner" style="width: 277.796px; margin-top: 0px;">
                <div class="sidebar-block sidebar-block_border">
                    <jsp:include page="/jsp/sidebarUser.jsp"/>
                    <jsp:include page="/jsp/participant/sidebarParticipant.jsp"/>
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
