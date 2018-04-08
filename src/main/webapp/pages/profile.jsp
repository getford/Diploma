<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.entity.PersonalInformationEntity" %>
<%@ page import="by.iba.uzhyhala.lot.LotHandler" %>
<%@ page import="by.iba.uzhyhala.user.Logout" %>
<%@ page import="by.iba.uzhyhala.user.Profile" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile - Auction Diploma</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        Profile profile = null;
        LotHandler lotHandler = null;

        List<PersonalInformationEntity> personalInformationEntityList = null;
        List<LotEntity> userLotList = null;
        if (cookieUtil.isFindCookie()) {
            profile = new Profile(request.getParameter("user").toLowerCase());
            lotHandler = new LotHandler(request.getParameter("user").toLowerCase());

            personalInformationEntityList = profile.getUserPersonalInformation();
            userLotList = lotHandler.getUserLot();
        } else {
            new Logout(request, response);
        }
        assert personalInformationEntityList != null;
    %>
</head>
<body>
<h1><%=profile.getUuidUser()%>
</h1>
<h1><%=personalInformationEntityList.get(0).getFirstName()%>
</h1>
<h1><%=personalInformationEntityList.get(0).getLastName()%>
</h1>
<h1><%=personalInformationEntityList.get(0).getBday()%>
</h1>
<hr/>


<div class="panel panel-success">
    <a href="#spoilerLot" class="btn btn-success btn-md btn-block" data-toggle="collapse"
       style="text-align: center;">
        <h4>Lots</h4>
    </a>
    <div id="spoilerLot" class="collapse">
        <div class="panel-body">
            <input class="form-control" id="lotInput" type="text" placeholder="Search..">
            <br>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Info</th>
                    <th>Status</th>
                    <%--<th>Category</th>--%>
                    <th>Cost</th>
                    <th>Blitz Cost</th>
                    <th>Step Cost</th>
                    <th>Date add</th>
                    <th>Date start</th>
                    <th>Date end</th>
                    <th>Time start</th>
                    <th>Time end</th>
                    <th>Control</th>
                </tr>
                </thead>
                <%
                    for (int i = 0; i < userLotList.size(); i++) {
                        String id = userLotList.get(i).getUuid();
                        String name = userLotList.get(i).getName();
                        String information = userLotList.get(i).getInformation();
                        String status = userLotList.get(i).getStatus();
                        /*String categoty = CommonUtil.getCategoryById();*/
                        String cost = userLotList.get(i).getCost();
                        String blitzCost = userLotList.get(i).getBlitzCost();
                        String stepCost = userLotList.get(0).getStepCost();
                        String dateAdd = String.valueOf(userLotList.get(i).getDateAdd());
                        String dateStart = String.valueOf(userLotList.get(i).getDateStart());
                        String dateEnd = String.valueOf(userLotList.get(i).getDateEnd());
                        String timeStart = String.valueOf(userLotList.get(i).getTimeStart());
                        String timeEnd = String.valueOf(userLotList.get(i).getTimeEnd());
                %>
                <tbody id="lotTable">
                <tr>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=name%>
                    </a>
                    </td>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=information%>
                    </a>
                    </td>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=status%>
                    </a>
                    </td>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=cost%>
                    </a>
                    </td>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=blitzCost%>
                    </a>
                    </td>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=stepCost%>
                    </a>
                    </td>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=dateAdd%>
                    </a>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=dateStart%>
                    </a>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=dateEnd%>
                    </a>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=timeStart%>
                    </a>
                    </td>
                    <td><a href="/lot.jsp?uuid=<%=id%>"><%=timeEnd%>
                    </a>
                    </td>
                    <td>
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
</body>
</html>