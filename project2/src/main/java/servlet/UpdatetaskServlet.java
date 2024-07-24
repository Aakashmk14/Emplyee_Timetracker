package servlet;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Task;
import DAO.TaskDAO;  // Adjust import according to your project structure
import model.timeup; // Assuming you have a class to calculate time

@WebServlet("/UpdateTaskServlet")
public class UpdatetaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String date = request.getParameter("date");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String project = request.getParameter("project");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            TaskDAO taskDAO = new TaskDAO();
            timeup timeCalculator = new timeup(); // Assuming you have this utility
            
            try {
                // Calculate total duration
                boolean isValidDuration = timeCalculator.isValidDuration(startTime, endTime);
                
                // Check for duplicates
                boolean isUniqueEntry = taskDAO.isUniqueEntry(date, startTime, endTime, id);
                
                if (!isValidDuration) {
                    request.setAttribute("errorMessage", "The total duration of the task cannot exceed 8 hours.");
                    request.getRequestDispatcher("/updateTaskForm.jsp?id=" + id).forward(request, response);
                    return;
                }
                
                if (!isUniqueEntry) {
                    request.setAttribute("errorMessage", "A task with the same time duration already exists for this day.");
                    request.getRequestDispatcher("/updateTaskForm.jsp?id=" + id).forward(request, response);
                    return;
                }
                
                // Create task object and update in the database
                Task task = new Task(id, null, date, startTime, endTime, category, description, project);
                taskDAO.updateTask(task);
                
                // Redirect to the task list page
                response.sendRedirect("ViewTasksServlet");
                
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQL exception (e.g., show an error message)
                request.setAttribute("errorMessage", "An error occurred while updating the task.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            // Handle missing task ID (e.g., redirect to the task list page with an error message)
            request.setAttribute("errorMessage", "Task ID is missing.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
