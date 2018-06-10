<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.USER" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.QUERY_CHART_DATE_CREATE_USER" %>
<%@ page import="static by.iba.uzhyhala.lot.LotControl.getAllLots" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.getUserLoginByUUID" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.getUserFirstLastNameByUUID" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.getUUIDUserByUUIDLot" %>
<%@ page import="java.util.List" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.LOT" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage lots - Auction Diploma</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Zhyhala">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet'
          type='text/css'>
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/templatemo-style.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/scripts/index/jquery.js"></script>
    <script type="text/javascript" src="/resources/scripts/index/main_head.js"></script>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        if (!cookieUtil.isAdmin())
            response.sendRedirect("/pages/index.jsp");
        List<LotEntity> lotEntityList = getAllLots();
    %>
</head>
<body>
<!-- Left column -->
<div class="templatemo-flex-row">
    <div class="templatemo-sidebar">
        <header class="templatemo-site-header">
            <h1>ADMIN. AUCTION</h1>
        </header>
        <div class="mobile-menu-icon">
            <i class="fa fa-bars"></i>
        </div>
        <nav class="templatemo-left-nav">
            <ul>
                <li><a href="/pages/admin.jsp"><i class="fa fa-home fa-fw"></i>Главная</a></li>
                <li><a href="/pages/charts.jsp"><i class="fa fa-bar-chart fa-fw"></i>Графики</a></li>
                <li><a href="/pages/manageusers.jsp"><i class="fa fa-users fa-fw"></i>Пользователи</a></li>
                <li><a href="#" class="active"><i class="fa fa-sliders fa-fw"></i>Лоты</a></li>
                <li><a href="/pages/index.jsp"><i class="fa fa-eject fa-fw"></i>На сайт</a></li>
            </ul>
        </nav>
    </div>
    <!-- Main content -->
    <div class="templatemo-content col-1 light-gray-bg">
        <div class="templatemo-content-container">
            <div class="templatemo-content-widget no-padding">
                <div class="panel panel-default table-responsive">
                    <table class="table table-striped table-bordered templatemo-user-table">
                        <thead>
                        <tr>
                            <td><a href="" class="white-text templatemo-sort-by">UUID <span class="caret"></span></a>
                            </td>
                            <td><a href="" class="white-text templatemo-sort-by">Название <span
                                    class="caret"></span></a>
                            </td>
                            <td><a href="" class="white-text templatemo-sort-by">Продавец <span
                                    class="caret"></span></a></td>
                            <td><a href="" class="white-text templatemo-sort-by">Дата создания <span
                                    class="caret"></span></a></td>
                            <td><a href="" class="white-text templatemo-sort-by">Статус <span
                                    class="caret"></span></a>
                            </td>
                            <td>Изменить</td>
                            <td>Удалить</td>
                        </tr>
                        </thead>
                        <%
                            assert lotEntityList != null;
                            for (LotEntity list : lotEntityList) {
                                String uuid = list.getUuid();
                                String name = list.getName();
                                String createBy = String.valueOf(getUserFirstLastNameByUUID(getUUIDUserByUUIDLot(list.getUuid())));
                                String dateCreate = String.valueOf(list.getDateAdd());
                                String status = list.getStatus();
                        %>
                        <tbody>
                        <tr>
                            <td><%=uuid%>
                            </td>
                            <td><%=name%>
                            </td>
                            <td><%=createBy%>
                            </td>
                            <td><%=dateCreate%>
                            </td>
                            <td><%=status%>
                            </td>
                            <td>
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-target="#exampleModal<%=uuid%>">
                                    Изменить лот
                                </button>
                                <!-- Modal -->
                                <div class="modal fade" id="exampleModal<%=uuid%>" tabindex="-1" role="dialog"
                                     aria-labelledby="exampleModalLabel"
                                     aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Изменение лота</h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <form action="/change?type=<%=LOT%>&uuid=<%=uuid%>" method="post">
                                                <div class="modal-body">

                                                    <input type="text" name="name_lot" placeholder="Название"
                                                           value="<%=name%>">
                                                    <br/>
                                                    <input type="text" name="info_lot" placeholder="Информация"
                                                           value="<%=list.getInformation()%>">
                                                    <br/>
                                                    <input type="text" name="cost_lot" placeholder="Цена"
                                                           value="<%=list.getCost()%>">
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-dismiss="modal">Закрыть
                                                    </button>
                                                    <button type="submit" name="btn_recovery_passcode"
                                                            class="btn btn-primary">Отправить
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <%--<td><a href="" class="templatemo-link">Action</a></td>--%>
                            <td>
                                <form action="/del" method="get" name="delete-form">
                                    <a href="/del?uuid-lot=<%=uuid%>" class="templatemo-link">Удалить</a>
                                </form>
                            </td>
                        </tr>
                        </tbody>
                        <%
                            }
                        %>
                    </table>
                </div>
            </div>
            <footer class="text-right">
                <p>Copyright &copy; 2018 Uladzimir Zhyhala inc.</p>
            </footer>
        </div>
    </div>
</div>

<!-- JS -->
<script type="text/javascript" src="/resources/scripts/jquery-1.11.2.min.js"></script>      <!-- jQuery -->
<script type="text/javascript" src="/resources/scripts/templatemo-script.js"></script>      <!-- Templatemo Script -->
<script>
    $(document).ready(function () {
        // Content widget with background image
        let imageUrl = $('index.content-bg-index').attr('src');
        $('.templatemo-content-index-bg').css('background-image', 'url(' + imageUrl + ')');
        $('index.content-bg-index').hide();
    });
</script>
</body>
</html>