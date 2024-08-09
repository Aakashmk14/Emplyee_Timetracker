package servlet;

import dao.TaskDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewEmployeeTasks")
public class ViewEmployeeTasksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO = new TaskDAO();
    
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
     // Set list as request attribute
	String employeeName = request.getParameter("employee");
    
    request.getSession().setAttribute("selectedEmployee", employeeName);
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
