<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<div class="sidebar-block sidebar-block_border">
    <div class="sidebar-block_header">
        <h4><fmt:message key="sidebar.language.label"/></h4>
    </div>
    <div class="sidebar-block_content">
        <div>
            <a href="javascript: void(0)" id="ru">
                <img src="img/lang/ru.jpg" class="lang_img"> Русский язык
            </a>
            <form id="to_ru" action="controller" method="POST">
                <input type="hidden" name="command" value="change_locale"/>
                <input type="hidden" name="locale" value="ru"/>
            </form>
        </div>
        <div>
            <a href="javascript: void(0)" id="by">
                <img src="img/lang/by.jpg" class="lang_img"> Беларуская мова
            </a>
            <form id="to_by" action="controller" method="POST">
                <input type="hidden" name="command" value="change_locale"/>
                <input type="hidden" name="locale" value="by"/>
            </form>
        </div>
        <div>
            <a href="javascript: void(0)" id="en">
                <img src="img/lang/en.jpg" class="lang_img">English language
            </a>
            <form id="to_en" action="controller" method="POST">
                <input type="hidden" name="command" value="change_locale"/>
                <input type="hidden" name="locale" value="en"/>
            </form>
        </div>
    </div>
</div>
<script src="<c:url value="/js/change_locale.js" />"></script>
</body>
</html>
