<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.lot.LotControl" %>
<%@ page import="by.iba.uzhyhala.lot.to.BetHistoryTO" %>
<%@ page import="by.iba.uzhyhala.util.CommonUtil" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="by.iba.uzhyhala.util.MailUtil" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="author" content="Zhyhala">
    <meta name="description" content="Auction Diploma Project index page"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/index/reset.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/index/main.css">
    <script type="text/javascript" src="/resources/scripts/index/jquery.js"></script>
    <script type="text/javascript" src="/resources/scripts/index/main_head.js"></script>

    <%-- FOR INPUT --%>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/fonts/input/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/demo.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/component.css"/>

    <title>Lot - Diploma Auction</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%
        URL url = new URL(String.valueOf(request.getRequestURL()));
        CookieUtil cookieUtil = new CookieUtil(request);
        LotControl lotControl = null;

        List<LotEntity> lotInfoList = null;
        List<BetHistoryTO> betHistoryList = null;
        String timeEnd = null;
        String host = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort();
        try {
            betHistoryList = CommonUtil.getHistoryBets(request.getParameter("uuid"));
            lotControl = new LotControl(request.getParameter("uuid"));
            lotInfoList = lotControl.getLotInfoByUuid();
            timeEnd = lotControl.returnEndTime();
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    %>
    <script>
        function startTimer(duration, display) {
            let timer = duration,
                minutes, seconds;

            let flag = true;
            setInterval(() => {
                if (!flag)
                    document.getElementById("btn-do-bet").disabled = true;
                if (flag) {
                    minutes = parseInt(timer / 60, 10);
                    seconds = parseInt(timer % 60, 10);

                    minutes = minutes < 10 ? "0" + minutes : minutes;
                    seconds = seconds < 10 ? "0" + seconds : seconds;

                    if (--timer < -1) {
                        requestToUpdateLot();
                        document.getElementById("btn-do-bet").hidden = true;
                        document.getElementById("input-cost-bet").hidden = true;
                        document.getElementById('div-sale').style.display = 'block';
                        alert("CLOSED");
                        flag = false;
                        display.textContent = "CLOSED";
                    } else {
                        display.textContent = minutes + ":" + seconds;
                    }
                }
            }, 1000);
        }

        window.onload = () => {
            let fiveMinutes = document.getElementById("start_ticks").value,
                display = document.querySelector('#time');
            startTimer(fiveMinutes, display);
        };

        function requestToUpdateLot() {
            $.ajax({
                crossDomain: true,
                type: "GET",
                url: document.getElementById("host").value + "/status?uuid=" + document.getElementById("_uuid_lot").value,
                success: (s) => {
                    console.log(s);
                }
            });
        }
    </script>
</head>
<body>

<header>

    <div class="wrapper">
        <a href="/pages/index.jsp" class="logo"><h1><span style="color: black">AUCTION DIPLOMA</span></h1> <%--<img src="/resources/images/index/logo.png" alt=""
                                                      title="Auction Diploma Project"/> --%></a>
        <nav>
            <ul>
                <li><a href="/pages/profile.jsp">Профиль</a></li>
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
        <p class="cap_title">Добавим лот?</p>
        <p class="cap_desc">Скоро тут будет шикар...подожди-подожди...ная площадка для торгов</p>
    </section>
</section><!-- End billboard -->

<br/><br/>
<section class="recent_work wrapper">
    <h3 class="S_title">Заполните все поля</h3>
    <span id="time"></span>
    <input type="hidden" value="<%=timeEnd%>" id="start_ticks">
    <input type="hidden" value="<%=host%>" id="host">
    <input type="hidden" id="_uuid_lot" value="<%=request.getParameter("uuid")%>">
    <hr/>
</section>
<!-- End recent_work -->

<footer>
    <div class="wrapper">
        <div class="f_cols">
            <p>BLR, Minsk<span class="phone"><%=VariablesUtil.EMAIL_SUPPORT%></span></p>
        </div>

        <%-- <div class="f_cols">
             <h3>Company</h3>
             <ul>
                 <li><a href="#">Our Story</a></li>
                 <li><a href="#">Mission</a></li>
                 <li><a href="#">Journal</a></li>
                 <li><a href="#">Careers</a></li>
             </ul>
         </div>--%>

        <div class="f_cols">
            <h3>Support</h3>
            <ul>
                <li><a href="#">FAQ</a></li>
                <li><a href="#">Contact Us</a></li>
                <li><a href="#">Policies</a></li>
            </ul>
        </div>

        <div class="f_cols">
            <p>Copyright &copy; 2018<span class="phone"> Uladzimir Zhyhala inc.</span></p>
        </div>

    </div>
</footer><!-- End footer -->
</body>

<%--
</head>
<body>
<span id="time"></span>
<input type="hidden" value="<%=timeEnd%>" id="start_ticks">
<input type="hidden" value="<%=host%>" id="host">
<input type="hidden" id="_uuid_lot" value="<%=request.getParameter("uuid")%>">
<hr/>
<br/>
<br/>
<br/>
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
<form action="/generatehistorybets" method="post">
    <input type="hidden" name="uuid_lot" value="<%=request.getParameter("uuid")%>">
    <button type="submit" name="generate_doc">Генерировать отчет</button>
</form>
<hr/>
<form action="/bethandler" method="post">
    <h3>Сделать ставку</h3>
    <span id="div-sale" style="display: none;">ПРОДАНО</span>
    <input type="hidden" name="uuid_lot" value="<%=request.getParameter("uuid")%>">
    <input id="input-cost-bet" type="text" name="cost" placeholder="min bet ">
    <button id="btn-do-bet" type="submit" name="do_bet">Сделать ставку</button>
</form>
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
                            for (int i = 0; i < betHistoryList.size(); i++) {
                                if (i == 0)
                                    continue;
                                else {
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
                            }
                        %>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
--%>
