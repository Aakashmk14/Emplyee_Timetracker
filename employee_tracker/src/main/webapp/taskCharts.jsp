<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tasks Charts</title>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawCharts);

        function drawCharts() {
            // Daily Tasks Pie Chart
            var dailyData = google.visualization.arrayToDataTable([
                ['Date & Project', 'Task Count'],
                <%= request.getAttribute("dailyData") %>
            ]);
            var dailyOptions = {
                title: 'Daily Tasks',
                is3D: true,
            };
            var dailyChart = new google.visualization.PieChart(document.getElementById('dailyChart'));
            dailyChart.draw(dailyData, dailyOptions);

            // Weekly Tasks Bar Chart
            var weeklyData = google.visualization.arrayToDataTable([
                ['Week & Project', 'Task Count'],
                <%= request.getAttribute("weeklyData") %>
            ]);
            var weeklyOptions = {
                title: 'Weekly Tasks',
                hAxis: { title: 'Week', titleTextStyle: { color: '#333' } },
                vAxis: { minValue: 0 },
                legend: { position: 'none' }
            };
            var weeklyChart = new google.visualization.BarChart(document.getElementById('weeklyChart'));
            weeklyChart.draw(weeklyData, weeklyOptions);

            // Monthly Tasks Bar Chart
            var monthlyData = google.visualization.arrayToDataTable([
                ['Month & Project', 'Task Count'],
                <%= request.getAttribute("monthlyData") %>
            ]);
            var monthlyOptions = {
                title: 'Monthly Tasks',
                hAxis: { title: 'Month', titleTextStyle: { color: '#333' } },
                vAxis: { minValue: 0 },
                legend: { position: 'none' }
            };
            var monthlyChart = new google.visualization.BarChart(document.getElementById('monthlyChart'));
            monthlyChart.draw(monthlyData, monthlyOptions);
        }
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            width: 80%;
            margin: 0 auto;
        }
        .chart-container {
            margin-bottom: 40px;
        }
        .chart {
            width: 100%;
            height: 500px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Tasks Charts</h1>
        <div class="chart-container">
            <div id="dailyChart" class="chart"></div>
        </div>
        <div class="chart-container">
            <div id="weeklyChart" class="chart"></div>
        </div>
        <div class="chart-container">
            <div id="monthlyChart" class="chart"></div>
        </div>
    </div>
</body>
</html>
