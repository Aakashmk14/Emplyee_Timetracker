<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Select Employee</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        select, button {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Select Employee</h2>
        <form action="viewEmployeeTasks" method="post">
            <label for="employee">Employee:</label>
            <select name="employee" id="employee" required>
                <%
                    List<String> employees = (List<String>) request.getAttribute("employees");
                    for (String employee : employees) {
                        out.print("<option value=\"" + employee + "\">" + employee + "</option>");
                    }
                %>
            </select>
            <button type="submit">View Tasks</button>
        </form>
    </div>
</body>
</html>
