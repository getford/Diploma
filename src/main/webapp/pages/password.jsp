<%@ page import="by.iba.uzhyhala.util.MailUtil" %>
<%@ page import="by.iba.uzhyhala.util.CommonUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change password</title>
</head>
<body>

<form action="/forgetpassword" method="post">
    <%
        if (request.getParameter("fp").equals(String.valueOf(Boolean.FALSE))) {
    %>
    <input class="input__field input__field--isao" type="text" id="input-1" name="old_password"/>
    <%
        }
    %>
    <input class="input__field input__field--isao" type="text" id="input-1" name="new_password"/>
    <input class="input__field input__field--isao" type="text" id="input-1" name="new_password_"/>

    <input type="hidden" name="uuid" value="<%=request.getParameter("uuid")%>">
    <input type="hidden" name="fp" value="<%=request.getParameter("fp")%>">
    <button type="submit" name="change_password">Изменить пароль</button>

</form>
</body>
</html>
