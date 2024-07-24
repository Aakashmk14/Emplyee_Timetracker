package servlet;

import DAO.TaskDAO;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/taskList")
public class taskServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    @Override
    public void init() {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeName = (String) request.getSession().getAttribute("employeeName");
        if (employeeName == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if not logged in
            return;
        }

        try {
            List<Task> tasks = taskDAO.getTasksByEmployeeName(employeeName);
            request.setAttribute("tasks", tasks);
            request.getRequestDispatcher("updateTask.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching tasks.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
