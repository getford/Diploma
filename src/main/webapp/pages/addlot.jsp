<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add lot - Auction Diploma</title>
    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        String uuidUser = cookieUtil.getUserUuidFromToken();
    %>
</head>
<body>
<form action="/lothandler" method="post">
    <input type="hidden" name="uuid_user" value="<%=uuidUser%>"/>
    Имя лота: <input type="text" name="name_lot" placeholder="Имя"/>
    <br/>
    Информация: <input type="text" name="info_lot" placeholder="Информация"/>
    <br/>
    Цена: <input type="text" name="cost" placeholder="Цена"/>
    <br/>
    Блиц-цена: <input type="text" name="blitz" placeholder="Блиц"/>
    <br/>
    Минимальный шаг: <input type="text" name="step" placeholder="Шаг"/>
    <br/>
    Дата старта торгов: <input type="date" name="date_start" placeholder="Дата начала"/>
    <br/>
    Дата окончания торгов: <input type="date" name="date_end" placeholder="Дата окончания"/>
    <br/>
    Время начала: <input type="time" name="time_start" placeholder="Время начала"/>
    <br/>
    Время окнончания: <input type="time" name="time_end" placeholder="Время окнчания"/>
    <br/>
    Категория: <input type="text" name="cat" placeholder="Категория"/>
    <br/>

    <button type="submit" name="login">Log in</button>
</form>
</body>
</html>
