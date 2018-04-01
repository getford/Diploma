<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="/registration" method="post">
    Login: <input type="text" name="login" placeholder="login"/>
    <br/>
    Email: <input type="email" name="email" placeholder="email"/>
    <br/>
    Password: <input type="password" name="password" placeholder="password"/>
    <br/>
    <button type="submit" name="reg">REG</button>
</form>
</body>
</html>
