<%@ page import="by.iba.uzhyhala.entity.AuthInfoEntity" %>
<%@ page import="by.iba.uzhyhala.util.CommonUtil" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.USER" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.QUERY_CHART_DATE_CREATE_USER" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage users - Auction Diploma</title>
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

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        if(!cookieUtil.isAdmin())
            response.sendRedirect("/pages/index.jsp");
        List<AuthInfoEntity> authInfoEntityList = CommonUtil.getAllUser();
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
                <li><a href="/pages/admin.jsp"><i class="fa fa-home fa-fw"></i>Dashboard</a></li>
                <li><a href="/pages/charts.jsp"><i class="fa fa-bar-chart fa-fw"></i>Графики</a></li>
                <li><a href="#" class="active"><i class="fa fa-users fa-fw"></i>Пользователи</a></li>
                <li><a href="/pages/managelots.jsp"><i class="fa fa-sliders fa-fw"></i>Лоты</a></li>
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
                            <td><a href="" class="white-text templatemo-sort-by">Login <span class="caret"></span></a>
                            </td>
                            <td><a href="" class="white-text templatemo-sort-by">Email <span
                                    class="caret"></span></a></td>
                            <td><a href="" class="white-text templatemo-sort-by">Role <span
                                    class="caret"></span></a></td>
                            <td><a href="" class="white-text templatemo-sort-by">Date registration <span
                                    class="caret"></span></a>
                            </td>
                            <td>Edit</td>
                            <td>Delete</td>
                        </tr>
                        </thead>
                        <%
                            assert authInfoEntityList != null;
                            for (AuthInfoEntity list : authInfoEntityList) {
                                String userUuid = list.getUuid();
                                String login = list.getLogin();
                                String email = String.valueOf(list.getEmail());
                                String dateRegistration = String.valueOf(list.getCreateDate());
                                String role = list.getRole();
                        %>
                        <tbody>
                        <tr>
                            <td><%=userUuid%>
                            </td>
                            <td><%=login%>
                            </td>
                            <td><%=email%>
                            </td>
                            <td><%=role%>
                            </td>
                            <td><%=dateRegistration%>
                            </td>
                            <td><a href="" class="templatemo-edit-btn">Edit</a></td>
                            <%--<td><a href="" class="templatemo-link">Action</a></td>--%>
                            <td>
                                <form action="/del" method="get" name="delete-form">
                                    <a href="/del?uuid-user=<%=userUuid%>" class="templatemo-link">Delete</a>
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