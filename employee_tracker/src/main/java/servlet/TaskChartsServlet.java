package servlet;
import dao.TaskDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/taskCharts")
public class TaskChartsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO = new TaskDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String employeeName = (String) session.getAttribute("name");

        if (employeeName == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<String[]> dailyTasks = taskDAO.getDailyTasks(employeeName);
        List<String[]> weeklyTasks = taskDAO.getWeeklyTasks(employeeName);
        List<String[]> monthlyTasks = taskDAO.getMonthlyTasks(employeeName);

        StringBuilder dailyData = new StringBuilder();
        for (String[] row : dailyTasks) {
            dailyData.append("['").append(row[0]).append("', ").append(row[1]).append("],");
        }

        StringBuilder weeklyData = new StringBuilder();
        for (String[] row : weeklyTasks) {
            weeklyData.append("['").append(row[0]).append("', ").append(row[1]).append("],");
        }

        StringBuilder monthlyData = new StringBuilder();
        for (String[] row : monthlyTasks) {
            monthlyData.append("['").append(row[0]).append("', ").append(row[1]).append("],");
        }

        request.setAttribute("dailyData", dailyData.toString());
        request.setAttribute("weeklyData", weeklyData.toString());
        request.setAttribute("monthlyData", monthlyData.toString());

        request.getRequestDispatcher("taskCharts.jsp").forward(request, response);
    }
}