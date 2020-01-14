<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<body>
<div class="header header_absolute">
    <div class="header_main">
        <div class="header_inner">
            <div class="header_item header_logo">
                <div class="logo">
                    <a href="/Beerfest"> <img src="/Beerfest/img/logo.png" class="logo_img"></a>
                </div>
            </div>
            <div class="header_item">
                <div class="header-menu">
                    <div class="header-menu_item">
                        <a href="javascript: void(0)" id="news">Новости</a>
                        <form id="update" action="controller" method="POST">
                            <input type="hidden" name="command" value="feed_update"/>
                        </form>
                    </div>
                    <div class="header-menu_item">
                        <a href="javascript: void(0)" id="gallery">Галерея</a>
                        <form id="galleryOpen" action="controller" method="POST">
                            <input type="hidden" name="command" value=""/>
                        </form>
                    </div>
                    <div class="header-menu_item">
                        <a href="javascript: void(0)" id="participants">Участники</a>
                        <form id="participantsOpen" action="controller" method="POST">
                            <input type="hidden" name="command" value="participant_list_update"/>
                        </form>
                    </div>
                    <div class="header-menu_item">
                        <a href="javascript: void(0)" id="rules">Правила</a>
                        <form id="rulesOpen" action="controller" method="POST">
                            <input type="hidden" name="command" value=""/>
                        </form>
                    </div>
                </div>

            </div>
            <div class="header_item">
                <div class="header-right-menu" style="align-items: center">
                    <div class="header-right_menu_item" style="display: <c:if test="${displayUser == null}">none</c:if>">
                        <a href="javascript:void(0)" id="profile" class="user_avatar">
                            <img data-src="${userAvatarUrl}" src="${userAvatarUrl}"
                                 class="avatar avatar_medium" alt="...">
                            <form id="profileOpen" action="controller" method="POST">
                                <input type="hidden" name="command" value="profile"/>
                            </form>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
