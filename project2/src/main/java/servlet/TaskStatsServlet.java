package servlet;

import DAO.TaskDAO;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/TaskStatsServlet")
public class TaskStatsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String employeeName = (String) session.getAttribute("name");
        
        if (employeeName == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if not logged in
            return;
        }

        TaskDAO taskDAO = new TaskDAO();
        try {
            List<Task> tasks = taskDAO.getTasksByEmployeeName(employeeName);
            
            // Calculate total durations for daily, weekly, and monthly tasks
            Map<String, Integer> dailyStats = taskDAO.calculateTaskDuration(tasks, "daily");
            Map<String, Integer> weeklyStats = taskDAO.calculateTaskDuration(tasks, "weekly");
            Map<String, Integer> monthlyStats = taskDAO.calculateTaskDuration(tasks, "monthly");

            // Set attributes for JSP
            request.setAttribute("dailyStats", dailyStats);
            request.setAttribute("weeklyStats", weeklyStats);
            request.setAttribute("monthlyStats", monthlyStats);

            // Forward to JSP
            request.getRequestDispatcher("taskstats.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve task stats");
        }
    }
}
