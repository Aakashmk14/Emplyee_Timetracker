<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Task</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f9f9f9;
        }

        .container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 400px;
            width: 100%;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        label {
            display: block;
            margin-bottom: 5px;
            text-align: left;
            width: 100%;
        }

        input[type="text"],
        input[type="date"],
        input[type="time"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        textarea {
            resize: vertical;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        a.button {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 20px;
            background-color: #4CAF50;
            color: white;
            text-align: center;
            border-radius: 4px;
            text-decoration: none;
        }

        a.button:hover {
            background-color: #45a049;
        }

        .error {
            color: red;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Add Task</h2>
        
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% String successMessage = (String) request.getSession().getAttribute("successMessage"); %>
        <% if (errorMessage != null) { %>
            <div class="error"><%= errorMessage %></div>
        <% } %>
        <% if (successMessage != null) { %>
            <div class="success"><%= successMessage %></div>
        <% } %>
        

        <form action="addtaskServlet" method="post">
            <label for="employeeName">Employee Name:</label>
            <input type="text" id="employeeName" name="employeeName" required>

            <label for="project">Project:</label>
            <input type="text" id="project" name="project" required>

            <label for="date">Date:</label>
            <input type="date" id="date" name="date" required>

            <label for="startTime">Start Time:</label>
            <input type="time" id="startTime" name="startTime" required>

            <label for="endTime">End Time:</label>
            <input type="time" id="endTime" name="endTime" required>

            <label for="category">Task Category:</label>
            <input type="text" id="category" name="category" required>

            <label for="description">Description:</label>
            <textarea id="description" name="description" required></textarea>

            <input type="submit" value="Add Task">
        </form>

        <a href="associateDashboard.jsp" class="button">Back to Dashboard</a>
    </div>
</body>
</html>
