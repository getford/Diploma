google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(lineLotAddChart);

function lineLotAddChart(chartArray) {
    let data = new google.visualization.DataTable();
    data.addColumn('string', 'Date');
    data.addColumn('number', 'Add lots for date');
    data.addRows([chartArray]);

    console.log(chartArray);

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