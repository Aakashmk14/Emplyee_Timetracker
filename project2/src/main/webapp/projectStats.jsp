<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <title>Project Statistics</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        #projectPieChart {
            max-width: 800px;
            margin: auto;
        }
    </style>
</head>
<body>
    <h2>Project Statistics</h2>
    
    <!-- Display Project Stats in a Table -->
    <table>
        <thead>
            <tr>
                <th>Project</th>
                <th>Total Minutes</th>
                <th>Employee</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Retrieve lists from request attributes
                List<String> projects = (List<String>) request.getAttribute("projects");
                List<Integer> minutes = (List<Integer>) request.getAttribute("minutes");
                List<String> employees = (List<String>) request.getAttribute("employees");

                if (projects != null && minutes != null && employees != null) {
                    for (int i = 0; i < projects.size(); i++) {
                        %>
                        <tr>
                            <td><%= projects.get(i) %></td>
                            <td><%= minutes.get(i) %></td>
                            <td><%= employees.get(i) %></td>
                        </tr>
                        <%
                    }
                }
            %>
        </tbody>
    </table>

    <!-- Pie Chart for Projects and Timings -->
    <canvas id="projectPieChart" width="400" height="400"></canvas>
    <script>
        var ctx = document.getElementById('projectPieChart').getContext('2d');
        var projectPieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: [
                    <%
                        // Output project names for pie chart labels
                        for (int i = 0; i < projects.size(); i++) {
                            if (i > 0) out.print(",");
                            out.print("'" + projects.get(i) + "'");
                        }
                    %>
                ],
                datasets: [{
                    label: 'Project Timings',
                    data: [
                        <%
                            // Output total minutes for pie chart data
                            for (int i = 0; i < minutes.size(); i++) {
                                if (i > 0) out.print(",");
                                out.print(minutes.get(i));
                            }
                        %>
                    ],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                var label = tooltipItem.label || '';
                                if (label) {
                                    label += ': ';
                                }
                                label += tooltipItem.raw + ' minutes';
                                return label;
                            }
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>
