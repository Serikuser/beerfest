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
                    <label for="barSubmit"><fmt:message key="panel.participant.label"/></label>
                    <div class="auth_field">
                        <div class="auth_error"> ${baRErrorMessage}</div>
                    </div>
                    <form id="barSubmit" method="POST" action="controller" autocomplete="off">
                        <input type="hidden" name="command" value="submit_bar"/>
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
                                               placeholder="<fmt:message key="panel.participant.placeholder.barname"/>"
                                               aria-label="<fmt:message key="panel.participant.placeholder.barname"/>"
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
                                               placeholder="<fmt:message key="panel.participant.placeholder.places"/>"
                                               aria-label="Recipient's username" aria-describedby="basic-addon2">
                                        <div class="input-group-append">
                                            <span class="input-group-text" id="basic-addon2">
                                                <i class="fas fa-utensils"></i></span>
                                        </div>
                                    </div>
                                    <label for="barDescription"><fmt:message key="panel.participant.description"/></label>
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
                            <fmt:message key="panel.participant.button"/>
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
