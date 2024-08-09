package servlet;

import dao.TaskDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/UpdateTaskServlet")
public class UpdatetaskServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    @Override
    public void init() {
        taskDAO = new TaskDAO(); // Initialize the TaskDAO object
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String idStr = request.getParameter("id");
        String project = request.getParameter("project");
        String dateStr = request.getParameter("date");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            try {
                // Validate task duration
                if (!taskDAO.isValidDuration(startTime, endTime)) {
                    request.setAttribute("errorMessage", "Task duration cannot exceed 8 hours.");
                    request.getRequestDispatcher("/updateTaskForm.jsp?id=" + id).forward(request, response);
                    return;
                }

                // Check for overlapping tasks, excluding the current task
                if (taskDAO.isOverlappingTask(date, startTime, endTime, id)) {
                    request.setAttribute("errorMessage", "Task overlaps with an existing task during this time period.");
                    request.getRequestDispatcher("/updateTaskForm.jsp?id=" + id).forward(request, response);
                    return;
                }

                // Update task in the database
                boolean success = taskDAO.updateTask(id,  project, date, startTime, endTime, category, description);
                if (success) {
                    response.sendRedirect("ViewTasksServlet"); // Redirect to task list or another appropriate page
                } else {
                    request.setAttribute("errorMessage", "Failed to update task.");
                    request.getRequestDispatcher("/updateTaskForm.jsp?id=" + id).forward(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "An error occurred while updating the task.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Task ID is missing.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}