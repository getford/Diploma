<%@ page import="by.iba.uzhyhala.admin.StatisticHandler" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Diploma Auction</title>

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="/resources/scripts/charts.js"></script>
    <%
        StatisticHandler statisticHandler = new StatisticHandler();
        String lineChartData = statisticHandler.getStatisticAddLotForDay();
    %>
</head>
<body>
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(lineLotAddChart);

    function lineLotAddChart() {
        let data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Add lots for date');
        data.addRows([<%=lineChartData%>]);

        console.log(<%=lineChartData%>);

        let options = {
            hAxis: {
                title: 'Date'
            },
            vAxis: {
                title: 'Count'
            }
        };

        let chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }
</script>
<div id="chart_div"></div>
</body>
</html>
<%--<script onload="lineLotAddChart(<%=lineChartData%>)"></script>--%>
