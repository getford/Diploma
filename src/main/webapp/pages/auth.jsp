<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auth - Auction Diploma</title>
</head>
<body>
<form action="/auth" method="post">
    Login or email: <input type="text" name="login_or_email" placeholder="login or email"/>
    <br/>
    Password: <input type="password" name="password" placeholder="password"/>
    <br/>
    <button type="submit" name="login">Log in</button>
</form>
</body>
</html>
