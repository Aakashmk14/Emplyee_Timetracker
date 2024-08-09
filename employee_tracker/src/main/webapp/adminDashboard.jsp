<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: TimesNewroman, serif;
            background-image: url('image/wall.jpg'); /* Add the path to your background image */
            background-size: cover;
            background-position: center;
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            color: dark black;
            
        }
        .dashboard {
            background-color: rgba(255, 255, 255, 0.20); /* Dark background with transparency */
            padding: 30px;
            border-radius: 10px;
            text-align: center;
            width: 100%;
            max-width: 400px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.30);
            
            
        }
        .dashboard h2 {
            margin-bottom: 20px;
        }
        .dashboard a {
            display: block;
            margin: 10px 0;
            padding: 15px;
            background-color: #abb1ba;
            color: black;
            text-decoration: none;
            text-align: center;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .dashboard a:hover {
            background-color: #0056b3;
        }
        .logout {
            position: absolute;
            top: 20px;
            right: 20px;
            text-decoration: none;
            color: white;
            padding: 10px 20px;
            background-color: #f21313; /* Red */
            border-radius: 8px;
            transition: background-color 0.3s;
        }
        .logout:hover {
            background-color: #a10d0d; /* Darker Red */
        }
    </style>
</head>
<body>
    <a href="login.jsp" class="logout">Logout</a>
    <div class="dashboard">
        <h2>Admin Dashboard</h2>
     
        <a href="register.jsp">Register New Employee</a>
        <a href="ViewProjectsServlet">View Task Statistics</a>
        <a href="selectEmployeeServlet">View Task of an Employee</a>
        <!-- Add other admin options here -->
    </div>
</body>
</html>
