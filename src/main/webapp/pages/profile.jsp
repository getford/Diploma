<%@ page import="by.iba.uzhyhala.entity.AddressEntity" %>
<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.entity.PersonalInformationEntity" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.ENTITY_LOT" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.*" %>
<%@ page import="static by.iba.uzhyhala.user.Profile.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile - Auction Diploma</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/index/reset.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/index/main.css">
    <script type="text/javascript" src="/resources/scripts/index/jquery.js"></script>
    <script type="text/javascript" src="/resources/scripts/index/main_head.js"></script>

    <%-- FOR INPUT --%>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/fonts/input/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/demo.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/component.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <%

        List<PersonalInformationEntity> personalInformationEntityList = null;
        List<AddressEntity> addressEntityList = null;
        List<LotEntity> userLotList = null;
        String userLogin = CookieUtil.getUserLoginFromCookie(request);
        String uuidUser = getUUIDUserByLoginEmail(request.getParameter("user").toLowerCase(), loginOrEmail(request.getParameter("user").toLowerCase()));
        if (StringUtils.isBlank(userLogin))
            response.sendRedirect("/pages/auth.jsp");
        else {
            personalInformationEntityList = getUserPersonalInformation(userLogin);
            addressEntityList = getUserAddress(userLogin);
            userLotList = getLots("SELECT l FROM " + ENTITY_LOT + " l WHERE uuidUserSeller = :uuid");
        }
        assert personalInformationEntityList != null;
    %>
</head>
<body>
<header>

    <div class="wrapper">
        <a href="/pages/index.jsp" class="logo">
            <img src="/resources/images/logo.jpg" alt=""
                 title="Auction Diploma Project"/></a> <nav>
            <ul>
                <li><a href="/pages/profile.jsp?user=<%=userLogin%>">Профиль</a>(<%=userLogin%>)</li>
                <li><a href="/pages/addlot.jsp">Добавить лот</a></li>
                <li><a href="/pages/auth.jsp">Авторизация/Регистрация</a></li>
                <li><a href="/pages/admin.jsp">Админ</a></li>
                <li><a href="">Обратная связь</a></li>
            </ul>
        </nav>
    </div>

</header><!-- End Header -->

<section class="billboard">
    <section class="caption">
        <p class="cap_title">Профиль</p>
    </section>
</section><!-- End billboard -->

<section class="recent_work wrapper">
    <br/>
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
                        <td><b>Uuid: </b><%=uuidUser%>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Имя: </b><%=personalInformationEntityList.get(0).getFirstName()%>   <%=personalInformationEntityList.get(0).getLastName()%>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Дата рождения: </b><%=personalInformationEntityList.get(0).getBday()%>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Рейтинг: </b><%=getRate(uuidUser, VariablesUtil.USER)%>
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
                            <b>Страна: </b><%=addressEntityList.get(0).getCountry()%>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Город: </b><%=addressEntityList.get(0).getCity()%>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Улица: </b><%=addressEntityList.get(0).getStreet()%>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Дом: </b><%=addressEntityList.get(0).getHouse()%>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Индекс: </b><%=addressEntityList.get(0).getZip()%>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <hr/>
    <form action="/changerate" method="post">
        <input type="hidden" name="login_or_email" value="<%=userLogin%>">
        <input type="hidden" name="uuid_" value="<%=uuidUser%>">
        <input type="hidden" name="type" value="<%=VariablesUtil.USER%>">
        <input type="hidden" name="goal" value="<%=VariablesUtil.RATE_PLUS%>">
        <button style="font-size:24px" type="submit" name="rate_plus"><i class="material-icons">thumb_up</i></button>
    </form>
    <form action="/changerate" method="post">
        <input type="hidden" name="login_or_email" value="<%=userLogin%>">
        <input type="hidden" name="uuid_" value="<%=uuidUser%>">
        <input type="hidden" name="type" value="<%=VariablesUtil.USER%>">
        <input type="hidden" name="goal" value="<%=VariablesUtil.RATE_MINUS%>">
        <button style="font-size:24px" type="submit" name="rate_plus"><i class="material-icons">thumb_down</i></button>
    </form>
    <a href="<%="/pages/password.jsp?uuid=" + uuidUser + "&fp=false"%>">Изменить пароль</a>
</section>

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
                                <%--<th>Control</th>--%>
                            </tr>
                            </thead>
                            <%
                                for (LotEntity lot : userLotList) {
                                    String id = lot.getUuid();
                                    String name = lot.getName();
                                    String information = lot.getInformation();
                                    String status = translateLotStatus(lot.getStatus());
                                    String category = getCategoryById(lot.getIdCategory());
                                    String cost = lot.getCost();
                                    String blitzCost = lot.getBlitzCost();
                                    String stepCost = userLotList.get(0).getStepCost();
                                    String dateAdd = String.valueOf(lot.getDateAdd());
                                    String dateStart = String.valueOf(lot.getDateStart());
                                    String dateEnd = String.valueOf(lot.getDateEnd());
                                    String timeStart = String.valueOf(lot.getTimeStart());
                                    String timeEnd = String.valueOf(lot.getTimeEnd());
                            %>
                            <tbody id="lotTable">
                            <tr>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=name%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=information%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=status%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=category%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=cost%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=blitzCost%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=stepCost%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=dateAdd%>
                                </a>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=dateStart%>
                                </a>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=dateEnd%>
                                </a>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=timeStart%>
                                </a>
                                </td>
                                <td><a href="/pages/lot.jsp?uuid=<%=id%>"><%=timeEnd%>
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
<!-- End recent_work -->

<footer>
    <div class="wrapper">
        <div class="f_cols">
            <p>BLR, Minsk<span class="phone"><%=VariablesUtil.EMAIL_SUPPORT%></span></p>
        </div>

        <div class="f_cols">
            <p>Copyright &copy; 2018<span class="phone"> Uladzimir Zhyhala inc.</span></p>
        </div>

    </div>
</footer><!-- End footer -->
</body>
</html>