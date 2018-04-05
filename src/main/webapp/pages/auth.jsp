<%@ page import="by.iba.uzhyhala.util.ReCaptchaUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auth - Auction Diploma</title>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
<form action="/auth" method="post">
    Login or email: <input type="text" name="login_or_email" placeholder="login or email"/>
    <br/>
    Password: <input type="password" name="password" placeholder="password"/>
    <br/>
    <button type="submit" name="login">Log in</button>
</form>
<br/>
<br/>
<form action="/registration" method="post">
    Login: <input type="text" name="login" placeholder="login"/>
    <br/>
    Email: <input type="email" name="email" placeholder="email"/>
    <br/>
    Password: <input type="password" name="password" placeholder="password"/>
    <br/>
    <div class="g-recaptcha" data-sitekey="<%=ReCaptchaUtil.PUBLIC%>"></div>
    <button type="submit" name="reg">REG</button>
</form>
</body>
</html>
