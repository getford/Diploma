<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.MESSAGE_ERROR_SERVER" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.getUserLoginByUUID" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение пароля</title>
</head>
<body>
<span>
Изменение пароля к учетной записи:<b> <%=getUserLoginByUUID(request.getParameter("uuid"))%></b>
</span>
<br/>
<hr/>
<form action="/forgetpassword" method="post">
    <%
        if (request.getParameter("fp").equals(String.valueOf(Boolean.FALSE))) {
    %>
    <input class="input__field input__field--isao" type="password" id="input-1" name="old_password"
           placeholder="Старай пароль"/><br/>
           <br/>
    <%
        }
    %>
    <input class="input__field input__field--isao" type="password" id="input-1" name="new_password"
           placeholder="Новый пароль"/><br/><br/>
    <input class="input__field input__field--isao" type="password" id="input-1" name="new_password_"
           placeholder="Повторите новый пароль"/><br/>
    <br/>
    <input type="hidden" name="uuid" value="<%=request.getParameter("uuid")%>">
    <input type="hidden" name="fp" value="<%=request.getParameter("fp")%>">
    <button type="submit" name="change_password">Изменить пароль</button>
</form>
<br/>
<br/>
<a href="/pages/index.jsp">Вернуться на сайт</a>
<hr/>
<span style="text-align: center"><%=MESSAGE_ERROR_SERVER%></span>
</body>
</html>
