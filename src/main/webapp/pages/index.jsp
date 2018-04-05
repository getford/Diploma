<%@ page import="by.iba.uzhyhala.lot.LotHandler" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index - Auction Diploma</title>
    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        LotHandler lotHandler = null;
        if (cookieUtil.isFindCookie()) {
            String user = "";
            lotHandler = new LotHandler(user);
        } else {
            lotHandler = new LotHandler();
        }
    %>
</head>
<body>
<a href="/pages/auth.jsp">auth</a>
<br/>
<a href="/logout">logout</a>
<<br/>
</body>
</html>