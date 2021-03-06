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
<%@ page import="static java.io.File.separator" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.FOLDER_UPLOAD_IMAGES" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.STATUS_LOT_SALES" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.STATUS_LOT_WAIT" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.getCurrentCost" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.getHistoryBets" %>
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
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

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
            betHistoryList = getHistoryBets(request.getParameter("uuid"));
            lotControl = new LotControl(request.getParameter("uuid"));
            lotInfoList = lotControl.getLotInfoByUuid();
            timeEnd = lotControl.returnEndTime();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
        }
        assert betHistoryList != null;
        assert lotInfoList != null;

        String imagesLot = separator + FOLDER_UPLOAD_IMAGES + separator + lotInfoList.get(0).getImagesName();
        int currentCost = getCurrentCost(betHistoryList) + Integer.parseInt(lotInfoList.get(0).getCost());
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
                        document.getElementById("time").hidden = true;
                        document.getElementById("title_time").hidden = true;
                        document.getElementById("do_bet_title").hidden = true;
                        document.getElementById('div-sale').style.display = 'block';
                        /*alert("ЛОТ ЗАКРЫТ");*/
                        flag = false;
                        display.textContent = "Лот закрыт";
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
        <a href="/pages/index.jsp" class="logo">
            <img src="/resources/images/logo.jpg" alt=""
                 title="Auction Diploma Project"/></a>
        <nav>
            <ul>
                <li><a href="/pages/profile.jsp">Профиль</a></li>
                <li><a href="/pages/addlot.jsp">Добавить лот</a></li>
                <li><a href="/pages/auth.jsp">Авторизация/Регистрация</a></li>
                <%
                    if (cookieUtil.isAdmin()) {
                %>
                <li><a href="/pages/admin.jsp">Админ</a></li>
                <%
                } else {
                %>
                <li><a style="pointer-events: none" href="/pages/admin.jsp">Админ</a></li>
                <%
                    }
                %>
                <li><a href="">Обратная связь</a></li>
            </ul>
        </nav>
    </div>
</header><!-- End Header -->

<section class="billboard">
    <section class="caption">
        <p class="cap_title">Информация о Лоте - <%=lotInfoList.get(0).getName()%>
        </p>
    </section>
</section><!-- End billboard -->

<br/><br/>
<section class="recent_work wrapper">
    <input type="hidden" value="<%=host%>" id="host">
    <input type="hidden" value="<%=lotInfoList.get(0).getStatus()%>" id="english_status">
    <input type="hidden" id="_uuid_lot" value="<%=request.getParameter("uuid")%>">
    <%
        assert timeEnd != null;
        if (lotInfoList.get(0).getStatus().equals(STATUS_LOT_WAIT)) {
    %> <h1>
    <span style="color: yellow">Лот в ожидании торгов.</span>
    <span style="color: yellow">Торги начнутся в <%=lotInfoList.get(0).getTimeStart()%></span></h1>
    <%
    } else if (!timeEnd.equals(VariablesUtil.STATUS_LOT_CLOSE) && !lotInfoList.get(0).getStatus().equals(STATUS_LOT_WAIT)) {
    %>
    <h1 id="title_time">Осталось времени: <span id="time"></span></h1>
    <input type="hidden" value="<%=timeEnd%>" id="start_ticks">
    <form action="/bethandler" method="post">
        <h3 id="do_bet_title">Сделать ставку: <span id="div-sale" style="display: none;">ПРОДАНО</span></h3>
        <input type="hidden" name="uuid_lot" value="<%=request.getParameter("uuid")%>">
        <input id="input-cost-bet" type="text" name="cost" placeholder="Не менее <%=lotInfoList.get(0).getStepCost()%>">
        <button id="btn-do-bet" type="submit" name="do_bet">Сделать ставку</button>
    </form>
    <hr/>
    <%
    } else {
        if (betHistoryList.size() != 1) {
    %>
    <h1>
        <span style="color: green">Победитель: <%=betHistoryList.get(betHistoryList.size() - 1).getUserName()%>, цена: <%=currentCost%> руб.</span>
    </h1>
    <%
    } else {
    %>
    <h1>
        <span style="color: red">Продажа лота не состоялась</span>
    </h1>
    <%
            }
        }
        if (lotInfoList.size() != 0) {
    %>
    <hr/>
    <h3 style="text-align: center"><span>Информация о лоте</span></h3>
    <div class="container">
        <div class="row">
            <div class="col-sm-5">
                <table class="table table-condensed">
                    <thead></thead>
                    <tbody>
                    <tr>
                        <td><b>UUID: </b><%=lotInfoList.get(0).getUuid()%>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Название: </b><%=lotInfoList.get(0).getName()%>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Описание: </b><%=lotInfoList.get(0).getInformation()%>
                        </td>
                    </tr>
                    <tr id="status_lot" onload="changeStatusColor();">
                        <td>
                            <b>Статус: </b><i
                                id="status_text"><%=CommonUtil.translateLotStatus(lotInfoList.get(0).getStatus())%>
                        </i>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Рейтинг: </b><%=CommonUtil.getRate(lotInfoList.get(0).getUuid(), VariablesUtil.LOT)%>
                        </td>
                    </tr>
                    <img src="<%=imagesLot%>" name="logo"/>
                    </tbody>
                </table>
            </div>

            <div class="col-sm-5">
                <table class="table table-condensed">
                    <thead></thead>
                    <tbody>
                    <tr>
                        <td>
                            <b>Стартовая цена: </b><%=lotInfoList.get(0).getCost()%> руб.
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Блиц цена: </b><%=lotInfoList.get(0).getBlitzCost()%> руб.
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Минимальная ставка: </b><%=lotInfoList.get(0).getStepCost()%> руб.
                        </td>
                    </tr>
                    <tr style="background-color: #d7d7d7">
                        <td>
                            <b>Текущая
                                цена: </b><%=currentCost%> руб.
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%
        }
    %>
    <hr/>
    <h3 style="text-align: center"><span>История ставок</span></h3>
    <br/>
    <div class="container">
        <div class="row">
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
                    for (int i = 0; i < betHistoryList.size(); i++) {
                        if (i == 0)
                            continue;
                        else {
                            String name = betHistoryList.get(i).getUserName();
                            String bet = String.valueOf(betHistoryList.get(i).getBet());
                            String dateBet = betHistoryList.get(i).getDate();
                            String timeBet = betHistoryList.get(i).getTime();
                %>
                <tbody>
                <%
                    if (i == betHistoryList.size() - 1) {
                %>
                <tr style="background-color: springgreen">
                    <td><%=name%>
                    </td>
                    <td><%=bet%>
                    </td>
                    <td><%=dateBet%>
                    </td>
                    <td><%=timeBet%>
                    </td>
                </tr>
                <%
                } else {
                %>
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
                <%
                    }
                %>
                </tbody>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </div>
    <br/>
    <hr/>
    <div class="container">
        <div class="row">
                <div class="panel panel-success">
                    <a href="#spoilerLot" class="btn btn-success btn-md btn-block" data-toggle="collapse"
                       style="">
                        <h4>Кнопки управления</h4>
                    </a>
                    <div id="spoilerLot" class="collapse">
                        <div class="panel-body">
                            <form action="/generatehistorybets" method="post">
                                <input type="hidden" name="uuid_lot" value="<%=request.getParameter("uuid")%>">
                                <input type="hidden" name="type" value="<%=VariablesUtil.PDF%>">
                                <input type="hidden" name="send-mail" value="true">
                                <button style="font-size:24px" type="submit"
                                        name="generate_doc_pdf_mail">PDF<i
                                        class="material-icons">mail</i>
                                </button>
                            </form>
                            <form action="/generatehistorybets" method="post">
                                <input type="hidden" name="uuid_lot" value="<%=request.getParameter("uuid")%>">
                                <input type="hidden" name="type" value="<%=VariablesUtil.EXCEL%>">
                                <input type="hidden" name="send-mail" value="true">
                                <button style="font-size:24px" type="submit"
                                        name="generate_doc_excel_mail">Excel<i
                                        class="material-icons">mail</i></button>
                            </form>
                            <form action="/generatehistorybets" method="post">
                                <input type="hidden" name="uuid_lot" value="<%=request.getParameter("uuid")%>">
                                <input type="hidden" name="type" value="<%=VariablesUtil.PDF%>">
                                <input type="hidden" name="send-mail" value="false">
                                <button style="font-size:24px" type="submit"
                                        name="generate_doc_pdf">PDF<i
                                        class="material-icons">file_download</i></button>
                            </form>
                            <form action="/generatehistorybets" method="post">
                                <input type="hidden" name="uuid_lot" value="<%=request.getParameter("uuid")%>">
                                <input type="hidden" name="type" value="<%=VariablesUtil.EXCEL%>">
                                <input type="hidden" name="send-mail" value="false">
                                <button style="font-size:24px" type="submit"
                                        name="generate_doc_excel">
                                    Excel<i class="material-icons">file_download</i>
                                </button>
                            </form>
                            <hr/>
                            <form action="/changerate" method="post">
                                <input type="hidden" name="uuid_" value="<%=request.getParameter("uuid")%>">
                                <input type="hidden" name="type" value="<%=VariablesUtil.LOT%>">
                                <input type="hidden" name="goal" value="<%=VariablesUtil.RATE_PLUS%>">
                                <button style="font-size:24px" type="submit" name="rate_plus"><i
                                        class="material-icons">thumb_up</i>
                                </button>
                            </form>
                            <form action="/changerate" method="post">
                                <input type="hidden" name="uuid_" value="<%=request.getParameter("uuid")%>">
                                <input type="hidden" name="type" value="<%=VariablesUtil.LOT%>">
                                <input type="hidden" name="goal" value="<%=VariablesUtil.RATE_MINUS%>">
                                <button style="font-size:24px" type="submit" name="rate_plus"><i class="material-icons">thumb_down</i>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</section>
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

<script>
    function changeStatusColor() {
        if (document.getElementById("english_status").value === "sales")
            document.getElementById("status_lot").style.color = "#1d9b07";

        // switch (document.getElementById("english_status").value) {
        //     case "active":
        //         document.getElementById("status_lot").style.color = "green";
        //         break;
        //     case "sales":
        //         document.getElementById("status_lot").style.color = "red";
        //         break;
        //     case "wait":
        //         document.getElementById("status_lot").style.color = "yellow";
        //         break;
        //     case "close":
        //         document.getElementById("status_lot").style.color = "red";
        //         break;
        //     default:
        //         document.getElementById("status_lot").style.color = "black";
        //         break;
        // }
    }
</script>
</html>