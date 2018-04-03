<%@ page import="by.iba.uzhyhala.util.VerifyRecaptchaUtil" %>
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
    <div class="g-recaptcha" data-sitekey="<%=VerifyRecaptchaUtil.PUBLIC%>"></div>
    <button type="submit" name="login">Log in</button>
</form>
</body>
</html>
