package servlet;
import DAO.TaskDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/addtaskServlet")
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
        String date = request.getParameter("date");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        try {
            // Parse start and end times
            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            // Calculate duration
            Duration duration = Duration.between(startTime, endTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;

            // Validate duration
            if (hours > 8 || (hours == 8 && minutes > 0)) {
                request.setAttribute("errorMessage", "Task duration cannot exceed 8 hours.");
                request.getRequestDispatcher("addTask.jsp").forward(request, response);
                return;
            }

            // Check for overlapping tasks
            LocalDate taskDate = LocalDate.parse(date);
            if (taskDAO.isDuplicateTask(taskDate, startTime, endTime, employeeName)) {
                request.setAttribute("errorMessage", "Overlapping task entry for the same date and time is not allowed.");
                request.getRequestDispatcher("addtask.jsp").forward(request, response);
                return;
            }

            // Add the task to the database and get the task ID
            int taskId = taskDAO.addTask(employeeName, project, taskDate, startTime, endTime, category, description);
            if (taskId != -1) {
                request.getSession().setAttribute("successMessage", "Task added successfully with ID: " + taskId);
                response.sendRedirect("addtask.jsp"); // Redirect to task list or another appropriate page
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
