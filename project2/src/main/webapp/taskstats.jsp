<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task Statistics</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        #taskBarChart {
            max-width: 800px;
            margin: auto;
        }
    </style>
</head>
<body>
    <h2>Task Statistics</h2>
    
    <!-- Bar Chart for Task Durations -->
    <canvas id="taskBarChart" width="400" height="400"></canvas>
    <script>
        var ctx = document.getElementById('taskBarChart').getContext('2d');
        var taskBarChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Daily', 'Weekly', 'Monthly'],
                datasets: [{
                    label: 'Task Duration (minutes)',
                    data: [
                        <%= request.getAttribute("dailyStats") != null ? ((Map<String, Integer>) request.getAttribute("dailyStats")).values().stream().mapToInt(Integer::intValue).sum() : 0 %>,
                        <%= request.getAttribute("weeklyStats") != null ? ((Map<String, Integer>) request.getAttribute("weeklyStats")).values().stream().mapToInt(Integer::intValue).sum() : 0 %>,
                        <%= request.getAttribute("monthlyStats") != null ? ((Map<String, Integer>) request.getAttribute("monthlyStats")).values().stream().mapToInt(Integer::intValue).sum() : 0 %>
                    ],
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>
</body>
</html>
