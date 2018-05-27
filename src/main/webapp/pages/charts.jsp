<%@ page import="by.iba.uzhyhala.admin.StatisticHandler" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Charts - Auction Diploma</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Visual Admin Dashboard - Data Visualization</title>
    <meta name="description" content="">
    <meta name="author" content="Zhyhala">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet'
          type='text/css'>
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/templatemo-style.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <%
        StatisticHandler statisticHandler = new StatisticHandler();
        String lineChartAddDateLot = statisticHandler.prepareChartDataFormat(QUERY_CHART_DATA_ADD_DATE_LOT, LOT);
        String lineChartStartDateLot = statisticHandler.prepareChartDataFormat(QUERY_CHART_DATA_START_DATE_LOT, LOT);
        String lineChartEndDateLot = statisticHandler.prepareChartDataFormat(QUERY_CHART_DATA_END_DATE_LOT, LOT);
    %>
</head>
<body>
<!-- Left column -->
<div class="templatemo-flex-row">
    <div class="templatemo-sidebar">
        <header class="templatemo-site-header">
            <div class="square"></div>
            <h1>Visual Admin</h1>
        </header>
        <div class="profile-photo-container">
            <img src="/resources/images/profile-photo.jpg" alt="Profile Photo" class="img-responsive">
            <div class="profile-photo-overlay"></div>
        </div>
        <!-- Search box -->
        <form class="templatemo-search-form" role="search">
            <div class="input-group">
                <button type="submit" class="fa fa-search"></button>
                <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
            </div>
        </form>
        <div class="mobile-menu-icon">
            <i class="fa fa-bars"></i>
        </div>
        <nav class="templatemo-left-nav">
            <ul>
                <li><a href="/pages/admin.jsp"><i class="fa fa-home fa-fw"></i>Dashboard</a></li>
                <li><a href="#" class="active"><i class="fa fa-bar-chart fa-fw"></i>Графики</a></li>
                <li><a href="/pages/manageusers.jsp"><i class="fa fa-users fa-fw"></i>Пользователи</a></li>
                <li><a href="preferences.html"><i class="fa fa-sliders fa-fw"></i>Лоты</a></li>
                <li><a href="login.html"><i class="fa fa-eject fa-fw"></i>Выход</a></li>
            </ul>
        </nav>
    </div>
    <!-- Main content -->
    <div class="templatemo-content col-1 light-gray-bg">
        <div class="templatemo-top-nav-container">
        </div>
        <div class="templatemo-content-container">
            <div class="templatemo-content-widget white-bg">
                <h2 class="margin-bottom-10">Data Visualization</h2>
                <div class="panel panel-default no-border">
                    <div class="panel-heading border-radius-10">
                        <h2>Добавлено лотов за день</h2>
                    </div>
                    <div id="chart_add_date_lot"></div>
                </div>
                <div class="panel panel-default no-border">
                    <div class="panel-heading border-radius-10">
                        <h2>Начато торгов за день</h2>
                    </div>
                    <div id="chart_start_date_lot"></div>
                </div>
                <div class="panel panel-default no-border">
                    <div class="panel-heading border-radius-10">
                        <h2>Продано за день</h2>
                    </div>
                    <div id="chart_end_date_lot"></div>
                </div>
            </div>
            <footer class="text-right">
                <p>Copyright &copy; 2018 Uladzimir Zhyhala inc.</p>
            </footer>
        </div>
    </div>
</div>

<!-- JS -->
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>      <!-- jQuery -->
<script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script> <!--  jQuery Migrate Plugin -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script> <!-- Google Chart -->
<script type="text/javascript" src="js/templatemo-script.js"></script>      <!-- Templatemo Script -->
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(lineLotAddChart);

    function lineLotAddChart() {
        let data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Add lots for date');
        data.addRows([<%=lineChartAddDateLot%>]);
        data.sort({column: 0, asc: true});

        console.log(<%=lineChartAddDateLot%>);

        let options = {
            hAxis: {
                title: 'Date'
            },
            vAxis: {
                title: 'Count'
            }
        };

        let chart = new google.visualization.LineChart(document.getElementById('chart_add_date_lot'));
        chart.draw(data, options);
    }
</script>
<%--Add lots today--%>
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(lineLotAddChart);

    function lineLotAddChart() {
        let data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Start date sale lots');
        data.addRows([<%=lineChartStartDateLot%>]);
        data.sort({column: 0, asc: true});

        console.log(<%=lineChartStartDateLot%>);

        let options = {
            hAxis: {
                title: 'Date'
            },
            vAxis: {
                title: 'Count'
            }
        };

        let chart = new google.visualization.LineChart(document.getElementById('chart_start_date_lot'));
        chart.draw(data, options);
    }
</script>
<%--Start date lots--%>
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(lineLotAddChart);

    function lineLotAddChart() {
        let data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'End date sale lots');
        data.addRows([<%=lineChartEndDateLot%>]);
        data.sort({column: 0, asc: true});

        console.log(<%=lineChartEndDateLot%>);

        let options = {
            hAxis: {
                title: 'Date'
            },
            vAxis: {
                title: 'Count'
            }
        };

        let chart = new google.visualization.LineChart(document.getElementById('chart_end_date_lot'));
        chart.draw(data, options);
    }
</script>
<%--End date lots--%>
</body>
</html>