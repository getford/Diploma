<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.lot.BetHandler" %>
<%@ page import="by.iba.uzhyhala.lot.LotControl" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="by.iba.uzhyhala.util.MailUtil" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lot - Diploma Auction</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%
        String uuidLot = request.getParameter("uuid");

        CookieUtil cookieUtil = new CookieUtil(request);
        LotControl lotControl = null;

        List<LotEntity> lotInfoList = null;
        try {
            lotControl = new LotControl(uuidLot);
            lotInfoList = lotControl.getLotInfoByUuid();
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
        }

    %>
</head>
<body>
<%
    assert lotInfoList != null;
    if (lotInfoList.size() != 0) {
%>
<h3><%=lotInfoList.get(0).getUuid()%>
</h3>
<h3><%=lotInfoList.get(0).getName()%>
</h3>
<h3><%=lotInfoList.get(0).getInformation()%>
</h3>
<%
    }
%>
<hr/>
<h3>History bets</h3>

<hr/>
<form action="/bethandler" method="post">
    <h3>Сделать ставку</h3>
    <input type="hidden" name="uuid_lot" value="<%=uuidLot%>">
    <input type="text" name="cost" placeholder="min bet ">
    <button type="submit" name="do_bet">Сделать ставку</button>
</form>
</body>
</html>
