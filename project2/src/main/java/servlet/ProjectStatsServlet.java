package servlet;
import DAO.TaskDAO;
import model.Task;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/ProjectStatsServlet")
public class ProjectStatsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDAO taskDAO = new TaskDAO();
        try {
            // Retrieve all tasks from the database
            List<Task> tasks = taskDAO.getAllTasks();
            
            // Calculate total minutes per project
            Map<String, Integer> projectStats = taskDAO.getProjectStats(tasks);
            
            // Separate the project names and total minutes for use in the JSP
            List<String> projects = new ArrayList<>(projectStats.keySet());
            List<Integer> minutes = new ArrayList<>(projectStats.values());
            
            // Extract employee information
            List<String> employees = new ArrayList<>();
            for (Task task : tasks) {
                employees.add(task.getEmployeeName());
            }

            // Set attributes for JSP
            request.setAttribute("projects", projects);
            request.setAttribute("minutes", minutes);
            request.setAttribute("employees", employees);

            // Forward to JSP
            request.getRequestDispatcher("projectStats.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve project stats");
        }
    }
}
