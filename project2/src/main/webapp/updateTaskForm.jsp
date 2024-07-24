<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Task" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Task</title>
    <style>
        form {
            margin: 20px;
        }
        label {
            display: block;
            margin: 5px 0;
        }
        input, textarea {
            width: 100%;
            margin-bottom: 10px;
        }
        button {
            padding: 10px 20px;
        }
    </style>
</head>
<body>
    <h2>Update Task</h2>
    
    <%-- Ensure the task object is set in request scope by the servlet --%>
    <%
        Task task = (Task) request.getAttribute("task");
        if (task != null) {
    %>
        <form action="UpdateTaskServlet" method="post">
            <input type="hidden" name="id" value="<%= task.getId() %>"/>
            
            <label>Date:</label>
            <input type="date" name="date" value="<%= task.getDate() %>" required/><br/>
            
            <label>Start Time:</label>
            <input type="time" name="startTime" value="<%= task.getStartTime() %>" required/><br/>
            
            <label>End Time:</label>
            <input type="time" name="endTime" value="<%= task.getEndTime() %>" required/><br/>
            
            <label>Category:</label>
            <input type="text" name="category" value="<%= task.getCategory() %>" required/><br/>
            
            <label>Description:</label>
            <textarea name="description" required><%= task.getDescription() %></textarea><br/>
            
            <label>Project:</label>
            <input type="text" name="project" value="<%= task.getProject() %>" required/><br/>
            
            <button type="submit">Update Task</button>
        </form>
    <% } else { %>
        <p>Task not found.</p>
    <% } %>
    
    <a href="ViewTasksServlet">Back to Task List</a>
</body>
</html>
