<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
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
        // Generate unique colors for each project
        function getRandomColor() {
            return 'rgba(' + Math.floor(Math.random() * 255) + ',' + 
                              Math.floor(Math.random() * 255) + ',' + 
                              Math.floor(Math.random() * 255) + ', 0.80)';
        }

        var projectColors = [];
        for (var i = 0; i < <%= projects.size() %>; i++) {
            projectColors.push(getRandomColor());
        }

        var ctx = document.getElementById('projectPieChart').getContext('2d');
        var projectPieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: [
                    <%
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
                            for (int i = 0; i < minutes.size(); i++) {
                                if (i > 0) out.print(",");
                                out.print(minutes.get(i));
                            }
                        %>
                    ],
                    backgroundColor: projectColors,
                    borderColor: projectColors.map(color => color.replace('0.2', '1')),
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
