<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.lot.LotHandler" %>
<%@ page import="java.util.List" %>
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
<%--    <%
        LotHandler lotHandler = new LotHandler();
        List<LotEntity> allLotsList = lotHandler.showAllLots();
    %>--%>
</head>
<body>
<br/>
<a href="/pages/auth.jsp">auth</a>
<br/>
<a href="/logout">logout</a>
<br/>
</body>
</html>