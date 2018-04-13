<%@ page import="by.iba.uzhyhala.admin.StatisticHandler" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Diploma Auction</title>

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="/resources/scripts/charts.js"></script>
    <%
        StatisticHandler statisticHandler = new StatisticHandler();
        String lineChartAddDateLot = statisticHandler.prepareChartDataFormat(VariablesUtil.QUERY_CHART_DATA_ADD_DATE_LOT);
        String lineChartStartDateLot = statisticHandler.prepareChartDataFormat(VariablesUtil.QUERY_CHART_DATA_START_DATE_LOT);
        String lineChartEndDateLot = statisticHandler.prepareChartDataFormat(VariablesUtil.QUERY_CHART_DATA_END_DATE_LOT);
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
        data.addRows([<%=lineChartAddDateLot%>]);

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
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(lineLotAddChart);

    function lineLotAddChart() {
        let data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Start date sale lots');
        data.addRows([<%=lineChartStartDateLot%>]);

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
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(lineLotAddChart);

    function lineLotAddChart() {
        let data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'End date sale lots');
        data.addRows([<%=lineChartEndDateLot%>]);

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
<div id="chart_add_date_lot"></div>
<div id="chart_start_date_lot"></div>
<div id="chart_end_date_lot"></div>
</body>
</html>
<%--<script onload="lineLotAddChart(<%=lineChartData%>)"></script>--%>
