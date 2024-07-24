<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Associate Dashboard</title>
    <style>
    /* styles.css */
     body {
     font-family: Arial, sans-serif;
     margin: 20px;
}

      h2 {
      color: #333;
}

      nav ul {
      list-style-type: none;
      padding: 0;
}

      nav ul li {
      display: inline;
      margin-right: 10px;
}

      nav ul li a {
      text-decoration: none;
      background-color: #4CAF50;
      color: white;
      padding: 10px 15px;
      border-radius: 4px;
}

      nav ul li a:hover {
      background-color: #45a049;
}
    
    </style>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Associate Dashboard</h2>
    <h2>Welcome, <%= session.getAttribute("name") %></h2>
    <nav>
        <ul>
            <li><a href="addtask.jsp">Add Task</a></li>
            <li><a href="ViewTasksServlet">Update Task</a></li>
            <li><a href="TaskStatsServlet">View Tasks</a></li>
        </ul>
    </nav>
</body>
</html>
