<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.lot.LotControl" %>
<%@ page import="by.iba.uzhyhala.to.BetHistoryTO" %>
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
        List<BetHistoryTO> betHistoryList = null;
        try {
            lotControl = new LotControl(uuidLot);
            lotInfoList = lotControl.getLotInfoByUuid();
            betHistoryList = lotControl.getHistoryBets();
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
<br/>
<div class="container">
    <div class="row">
        <div class="col-sm-10">
            <div class="panel panel-success">
                <a href="#betHistorySpoiler" class="btn btn-success btn-md btn-block" data-toggle="collapse"
                   style="text-align: center;">
                    <h4>History bets</h4>
                </a>
                <div class="panel-body">
                    <input class="form-control" id="betHistoryInput" type="text" placeholder="Search..">
                    <br>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Пользователь</th>
                            <th>Ставка</th>
                            <th>Дата</th>
                            <th>Время</th>

                        </tr>
                        </thead>
                        <%
                            assert betHistoryList != null;
                            for (int i = 1; i < betHistoryList.size(); i++) {
                                String name = betHistoryList.get(i).getUserName();
                                String bet = String.valueOf(betHistoryList.get(i).getBet());
                                String dateBet = betHistoryList.get(i).getDate();
                                String timeBet = betHistoryList.get(i).getTime();
                        %>
                        <tbody id="betHistory">
                        <tr>
                            <td><%=name%>
                            </td>
                            <td><%=bet%>
                            </td>
                            <td><%=dateBet%>
                            </td>
                            <td><%=timeBet%>
                            </td>
                        </tr>
                        </tbody>
                        <%
                            }
                        %>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<hr/>
<form action="/bethandler" method="post">
    <h3>Сделать ставку</h3>
    <input type="hidden" name="uuid_lot" value="<%=uuidLot%>">
    <input type="text" name="cost" placeholder="min bet ">
    <button type="submit" name="do_bet">Сделать ставку</button>
</form>
</body>
</html>
