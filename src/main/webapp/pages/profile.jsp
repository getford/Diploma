<%@ page import="by.iba.uzhyhala.entity.AddressEntity" %>
<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.entity.PersonalInformationEntity" %>
<%@ page import="by.iba.uzhyhala.lot.LotHandler" %>
<%@ page import="by.iba.uzhyhala.user.Profile" %>
<%@ page import="by.iba.uzhyhala.util.CommonUtil" %>
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
        List<AddressEntity> addressEntityList = null;
        List<LotEntity> userLotList = null;
        if (cookieUtil.isFindCookie()) {
            profile = new Profile(request.getParameter("user").toLowerCase());
            lotHandler = new LotHandler(request.getParameter("user").toLowerCase());

            personalInformationEntityList = profile.getUserPersonalInformation();
            addressEntityList = profile.getUserAddress();
            userLotList = lotHandler.getUserLot();
        } else {
            response.sendRedirect("/");
        }
        assert personalInformationEntityList != null;
    %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-5">
            <div class="panel panel-warning">
                <div class="panel-heading" style="text-align: center;"><h3>Profile info</h3>
                </div>
            </div>
            <table class="table table-condensed">
                <thead></thead>
                <tbody>
                <tr>
                    <td><b>Uuid: </b><%=profile.getUuidUser()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Name: </b><%=personalInformationEntityList.get(0).getFirstName()%>   <%=personalInformationEntityList.get(0).getLastName()%>
                    </td>
                </tr>
                <tr>
                    <td><b>Bday: </b><%=personalInformationEntityList.get(0).getBday()%>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="col-sm-5">
            <div class="panel panel-warning">
                <div class="panel-heading" style="text-align: center;"><h3>Address</h3>
                </div>
            </div>
            <table class="table table-condensed">
                <thead></thead>
                <tbody>
                <tr>
                    <td>
                        <b>Country: </b><%=addressEntityList.get(0).getCountry()%>
                    </td>
                </tr>
                <tr>
                    <td><b>City: </b><%=addressEntityList.get(0).getCity()%>
                    </td>
                </tr>
                <tr>
                    <td><b>Street: </b><%=addressEntityList.get(0).getStreet()%>
                    </td>
                </tr>
                <tr>
                    <td><b>House: </b><%=addressEntityList.get(0).getHouse()%>
                    </td>
                </tr>
                <tr>
                    <td><b>Zip: </b><%=addressEntityList.get(0).getZip()%>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-sm-10">
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
                                <th>Category</th>
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
                                    String category = CommonUtil.getCategoryById(userLotList.get(i).getIdCategory());
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
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=name%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=information%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=status%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=category%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=cost%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=blitzCost%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=stepCost%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=dateAdd%>
                                </a>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=dateStart%>
                                </a>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=dateEnd%>
                                </a>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=timeStart%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuidLot=<%=id%>"><%=timeEnd%>
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
        </div>
    </div>
</div>
</body>
</html>