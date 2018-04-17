<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Report - Auction Diploma</title>
</head>
<body>
<form action="/documenthandler" method="post">
    <div>
        <label>
            Все лоты:
            <input type="checkbox" name="all_lots">
        </label>
        <br/>
        <label>
            Активные лоты:
            <input type="checkbox" name="active_lots">
        </label>
        <br/>
        <label>
            Лоты в ожидании:
            <input type="checkbox" name="wait_lots">
        </label>
        <br/>
        <label> Проданные лоты:
            <input type="checkbox" name="sale_lots">
        </label>
        <br/>
        <label> Отмененные лоты:
            <input type="checkbox" name="close_lots">
        </label>
    </div>

    <button type="submit" name="generate_report">Генерировать отчет</button>
</form>
</body>
</html>
