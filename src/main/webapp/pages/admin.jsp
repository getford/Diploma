<%@ page import="by.iba.uzhyhala.admin.StatisticHandler" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="/resources/scripts/charts.js"></script>
    <title>Admin - Diploma Auction</title>
    <%
        StatisticHandler statisticHandler = new StatisticHandler();
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
                <li><a href="#" class="active"><i class="fa fa-home fa-fw"></i>Dashboard</a></li>
                <li><a href="/pages/charts.jsp"><i class="fa fa-bar-chart fa-fw"></i>Графики</a></li>
                <li><a href="/pages/manageusers.jsp"><i class="fa fa-users fa-fw"></i>Пользователели</a></li>
                <li><a href="/pages/managelots.jsp"><i class="fa fa-sliders fa-fw"></i>Лоты</a></li>
                <li><a href="/"><i class="fa fa-eject fa-fw"></i>Выход</a></li>
            </ul>
        </nav>
    </div>
    <!-- Main content -->
    <div class="templatemo-content col-1 light-gray-bg">
        <div class="templatemo-top-nav-container">
            <div class="row">
                <nav class="templatemo-top-nav col-lg-12 col-md-12">
                    <ul class="text-uppercase">
                        <%----%>
                    </ul>
                </nav>
            </div>
        </div>
        <div class="templatemo-content-container">
            <div class="templatemo-flex-row flex-content-row">
                <div class="templatemo-content-widget white-bg col-2">
                    <i class="fa fa-times"></i>
                    <div class="media margin-bottom-30">
                        <div style="text-align: center" class="media-body">
                            <h2 class="media-heading text-uppercase blue-text">Статистика за сегодня</h2>
                            <p>Лоты</p>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table">
                            <tbody>
                            <tr>
                                <td>
                                    <div class="circle green-bg"></div>
                                </td>
                                <td>Добавлено лотов за день</td>
                                <td><%=statisticHandler.countStatistic(VariablesUtil.QUERY_COUNT_ADD_LOT_TODAY)%>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="circle pink-bg"></div>
                                </td>
                                <td>Завершено лотов за день</td>
                                <td><%=statisticHandler.countStatistic(VariablesUtil.QUERY_COUNT_END_LOT_TODAY)%>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="circle blue-bg"></div>
                                </td>
                                <td>Начато торгов за день</td>
                                <td><%=statisticHandler.countStatistic(VariablesUtil.QUERY_COUNT_START_LOT_TODAY)%>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="templatemo-flex-row flex-content-row">
                <div class="col-1">
                    <div class="templatemo-content-widget orange-bg">
                        <i class="fa fa-times"></i>
                        <div class="media">
                            <div class="media-left">
                                <a href="#">
                                    <img class="media-object img-circle" src="/resources/images/sunset.jpg"
                                         alt="Sunset">
                                </a>
                            </div>
                            <div class="media-body">
                                <h2 class="media-heading text-uppercase">Consectur Fusce Enim</h2>
                                <p>Phasellus dapibus nulla quis risus auctor, non placerat augue consectetur.</p>
                            </div>
                        </div>
                    </div>
                    <div class="templatemo-content-widget white-bg">
                        <i class="fa fa-times"></i>
                        <div class="media">
                            <div class="media-left">
                                <a href="#">
                                    <img class="media-object img-circle" src="/resources/images/sunset.jpg"
                                         alt="Sunset">
                                </a>
                            </div>
                            <div class="media-body">
                                <h2 class="media-heading text-uppercase">Consectur Fusce Enim</h2>
                                <p>Phasellus dapibus nulla quis risus auctor, non placerat augue consectetur.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-1">
                    <div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
                        <i class="fa fa-times"></i>
                        <div class="panel-heading templatemo-position-relative"><h2 class="text-uppercase">Последние 3
                            зарегистрированных пользователя</h2></div>

                        <%--TODO: add last 3 users--%>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered">
                                <thead>
                                <tr>
                                    <td>No.</td>
                                    <td>First Name</td>
                                    <td>Last Name</td>
                                    <td>Username</td>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>1.</td>
                                    <td>John</td>
                                    <td>Smith</td>
                                    <td>@jS</td>
                                </tr>
                                <tr>
                                    <td>2.</td>
                                    <td>Bill</td>
                                    <td>Jones</td>
                                    <td>@bJ</td>
                                </tr>
                                <tr>
                                    <td>3.</td>
                                    <td>Mary</td>
                                    <td>James</td>
                                    <td>@mJ</td>
                                </tr>
                                <tr>
                                    <td>4.</td>
                                    <td>Steve</td>
                                    <td>Bride</td>
                                    <td>@sB</td>
                                </tr>
                                <tr>
                                    <td>5.</td>
                                    <td>Paul</td>
                                    <td>Richard</td>
                                    <td>@pR</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div> <!-- Second row ends -->
            <div class="templatemo-flex-row flex-content-row templatemo-overflow-hidden">
                <!-- overflow hidden for iPad mini landscape view-->
                <div class="col-1 templatemo-overflow-hidden">
                    <div class="templatemo-content-widget white-bg templatemo-overflow-hidden">
                        <i class="fa fa-times"></i>
                        <div class="templatemo-flex-row flex-content-row">
                            <div class="col-1 col-lg-6 col-md-12">
                                <h2 class="text-center">Modular<span class="badge">new</span></h2>
                                <div id="pie_chart_div" class="templatemo-chart"></div> <!-- Pie chart div -->
                            </div>
                            <div class="col-1 col-lg-6 col-md-12">
                                <h2 class="text-center">Interactive<span class="badge">new</span></h2>
                                <div id="bar_chart_div" class="templatemo-chart"></div> <!-- Bar chart div -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="text-right">
                <p>Copyright &copy; 2018 Uladzimir Zhyhala inc.</p>
            </footer>
        </div>
    </div>
</div>

<!-- JS -->
<script src="/resources/scripts/jquery-1.11.2.min.js"></script>      <!-- jQuery -->
<script src="/resources/scripts/jquery-migrate-1.2.1.min.js"></script> <!--  jQuery Migrate Plugin -->
<script src="https://www.google.com/jsapi"></script> <!-- Google Chart -->
<script>
    /* Google Chart
    -------------------------------------------------------------------*/
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {'packages': ['corechart']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);

    // Callback that creates and populates a data table,
    // instantiates the pie chart, passes in the data and
    // draws it.
    function drawChart() {

        // Create the data table.
        let data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data.addRows([
            ['Mushrooms', 3],
            ['Onions', 1],
            ['Olives', 1],
            ['Zucchini', 1],
            ['Pepperoni', 2]
        ]);

        // Set chart options
        let options = {'title': 'How Much Pizza I Ate Last Night'};

        // Instantiate and draw our chart, passing in some options.
        let pieChart = new google.visualization.PieChart(document.getElementById('pie_chart_div'));
        pieChart.draw(data, options);

        let barChart = new google.visualization.BarChart(document.getElementById('bar_chart_div'));
        barChart.draw(data, options);
    }

    $(document).ready(function () {
        if ($.browser.chrome) {
            //refresh page on browser resize
            // http://www.sitepoint.com/jquery-refresh-page-browser-resize/
            $(window).bind('resize', function (e) {
                if (window.RT) clearTimeout(window.RT);
                window.RT = setTimeout(function () {
                    this.location.reload(false);
                    /* false to get page from cache */
                }, 200);
            });
        } else {
            $(window).resize(function () {
                drawChart();
            });
        }
    });

</script>
<script type="text/javascript" src="resources/scripts/templatemo-script.js"></script>      <!-- Templatemo Script -->
</body>
</html>