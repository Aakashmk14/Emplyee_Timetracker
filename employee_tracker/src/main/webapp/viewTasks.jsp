<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Tasks</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
    <h2>Task List for <%= session.getAttribute("name") %></h2>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Employee Name</th>
                <th>Date</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Category</th>
                <th>Description</th>
                <th>Project</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                if (tasks != null && !tasks.isEmpty()) {
                    for (Task task : tasks) {
            %>
                <tr>
                    <td><%= task.getId() %></td>
                    <td><%= task.getEmployeeName() %></td>
                    <td><%= task.getDate() %></td>
                    <td><%= task.getStartTime() %></td>
                    <td><%= task.getEndTime() %></td>
                    <td><%= task.getCategory() %></td>
                    <td><%= task.getDescription() %></td>
                    <td><%= task.getProject() %></td>
                    <td>
                        <a href="ShowUpdateTaskFormServlet?id=<%= task.getId() %>">Update</a>
                        <a href="DeleteTaskServlet?id=<%= task.getId() %>" onclick="return confirm('Are you sure you want to delete this task?');">Delete</a>
                    </td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="9">No tasks found.</td>
                </tr>
            <% 
                }
            %>
        </tbody>
    </table>
</body>
</html>
