package servlet;

import dao.TaskDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/ViewProjectsServlet")
public class ViewProjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    @Override
    public void init() {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Map<String, Map<String, Integer>> projectDurations = taskDAO.getProjectDurationsByEmployee();

            List<String> projects = new ArrayList<>();
            List<Integer> minutes = new ArrayList<>();
            List<String> employees = new ArrayList<>();

            for (Map.Entry<String, Map<String, Integer>> entry : projectDurations.entrySet()) {
                String employeeName = entry.getKey();
                Map<String, Integer> employeeProjects = entry.getValue();
                
                for (Map.Entry<String, Integer> projectEntry : employeeProjects.entrySet()) {
                    projects.add(projectEntry.getKey());
                    minutes.add(projectEntry.getValue());
                    employees.add(employeeName);
                }
            }

            request.setAttribute("projects", projects);
            request.setAttribute("minutes", minutes);
            request.setAttribute("employees", employees);

            request.getRequestDispatcher("viewProjects.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error occurred while fetching project details.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
