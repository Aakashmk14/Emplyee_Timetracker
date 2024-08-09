<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('image/wall.jpg'); /* Add the path to your background image */
            background-size: cover;
            background-position: center;
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #333;
        }
        .container {
            width: 80%;
            max-width: 500px;
            background-color: rgba(255, 255, 255, 0.25); /* White background with slight transparency */
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            text-align: center;
        }
        #navbar {
            background: #333;
            color: #fff;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        #navbar a {
            color: #fff;
            text-decoration: none;
            padding: 10px 20px;
            display: inline-block;
            border-radius: 5px;
            transition: background 0.3s;
        }
        #navbar a:hover {
            background: #575757;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        p {
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <div id="navbar">
            <a href="addtask.jsp">Add Task</a>
            <a href="Viewandupdate">Update Task</a>
            <a href="taskCharts">Tasks Chart</a>
        </div>
        <h1>Welcome, <%= session.getAttribute("name") %>!</h1>
        <p>Select an option from the navigation bar.</p>
    </div>
</body>
</html>
