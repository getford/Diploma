<%@ page import="by.iba.uzhyhala.lot.LotHandler" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page import="by.iba.uzhyhala.util.CommonUtil" %>
<%@ page import="by.iba.uzhyhala.util.MailUtil" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index - Auction Diploma</title>
<%--
    <%
        try {
            CookieUtil cookieUtil = new CookieUtil(request);
            LotHandler lotHandler;
            if (cookieUtil.isFindCookie()) {
                String user = "";
                lotHandler = new LotHandler(user);
            } else {
                lotHandler = new LotHandler();
                lotHandler.showLots();
            }
        } catch (NoClassDefFoundError ex) {
            new MailUtil().sendErrorMailForAdmin(Arrays.toString(ex.getStackTrace()));
            response.getWriter().println(VariablesUtil.MESSAGE_ERROR_SERVER);
        }
    %>
--%>
</head>
<body>
<br/>
<a href="/pages/auth.jsp">auth</a>
<br/>
<a href="/logout">logout</a>
<br/>
</body>
</html>