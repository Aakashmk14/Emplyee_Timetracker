package servlet;

import dao.TaskDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    @Override
    public void init() {
        taskDAO = new TaskDAO(); // Initialize the TaskDAO object
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String employeeName = request.getParameter("employeeName");
        String project = request.getParameter("project");
        String dateStr = request.getParameter("date");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        try {
            // Parse date and times
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            // Validate task duration
            if (!taskDAO.isValidDuration(startTime, endTime)) {
                request.setAttribute("errorMessage", "Task duration cannot exceed 8 hours.");
                request.getRequestDispatcher("addtask.jsp").forward(request, response);
                return;
            }

            // Check for overlapping tasks
            if (taskDAO.isOverlappingTask(date, startTime, endTime, -1)) { // -1 means no task ID to exclude
                request.setAttribute("errorMessage", "Overlapping task entry for the same date and time is not allowed.");
                request.getRequestDispatcher("addtask.jsp").forward(request, response);
                return;
            }

            // Add the task to the database and get the task ID
            int taskId = taskDAO.addTask(employeeName, project, date, startTime, endTime, category, description);
            if (taskId != -1) {
                request.getSession().setAttribute("successMessage", "Task added successfully with ID: " + taskId);
                response.sendRedirect("ViewTasksServlet"); // Redirect to task list or another appropriate page
            } else {
                request.setAttribute("errorMessage", "Failed to add task.");
                request.getRequestDispatcher("addtask.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error occurred while processing the task.");
            request.getRequestDispatcher("addtask.jsp").forward(request, response);
        }
    }
}
