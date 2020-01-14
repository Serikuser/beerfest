<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Beer Festival News</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.css"
          rel="stylesheet">
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.js"></script>

</head>
<body>
<div class="app">
    <jsp:include page="/jsp/header.jsp"/>
    <div class="app_inner" style="padding-top: 64px">
        <div class="main">
            <div class="main_inner">
                <div class="feed">
                    <div class="auth_field">
                        <div class="auth_error"> ${bookErrorMessage}</div>
                    </div>
                    <c:forEach items="${participants}" var="bar">
                        <article class="news news_main">
                            <div class="container">
                                <h1><i class="fas fa-utensils"></i> ${bar.name}</h1>
                                <hr>
                                <h5>Описание бара:</h5>
                                <div class="well">
                                        ${bar.description}
                                </div>
                                <i class="fas fa-hamburger"></i>${bar.foodName}
                                <i class="fas fa-beer"></i>${bar.beerName}
                                <hr>
                            </div>

                            <div class="container" style="display: <c:if test="${signetGuestVisible == null}">none</c:if>">
                                <form id="book" method="POST" action="controller" autocomplete="off">
                                    <input type="hidden" name="command" value="make_book"/>
                                    <input type="hidden" name="barId" value="${bar.barId}"/>
                                    <div class="form-group">
                                        <h5>Бронирование:</h5>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-clock"></i></span>
                                            </div>
                                            <input size="16" name="bookDate" class="form-control" type="text"
                                                   placeholder="Выберете дату"
                                                   id="${bar.barId}" readonly/>
                                        </div>
                                        <div class="input-group mb-3">
                                            <select class="custom-select" id="places" name="bookPlaces">
                                                <option value=1>Столик на одного</option>
                                                <option value=2>Столик на двоих</option>
                                                <option value=4>Групповой столик (4 персоны)</option>
                                            </select>
                                            <div class="input-group-append">
                                                <label class="input-group-text" for="places">
                                                    <i class="fas fa-users"></i></label>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="submit" class="button_success button_width_30">
                                       Бронировать
                                    </button>
                                    <hr>
                                    <script>
                                        $('#${bar.barId}').datetimepicker({
                                            startDate:'2020/03/10',
                                            minDate:'2020/03/10',
                                            maxDate:'2020/03/13'
                                        });
                                    </script>
                                </form>
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

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>
<script src="https://kit.fontawesome.com/e9321a3cf3.js"></script>
<script src="<c:url value="/js/auth_form.js" />"></script>
<script src="<c:url value="/js/validation.js" />"></script>
<script src="<c:url value="/js/header.js" />"></script>

</body>
</html>
