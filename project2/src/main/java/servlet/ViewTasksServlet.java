package servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DAO.TaskDAO;
import model.Task;

@WebServlet("/ViewTasksServlet")
public class ViewTasksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("name");
        
        if (username != null) {
            TaskDAO taskDAO = new TaskDAO();
            List<Task> tasks = null;

            try {
                tasks = taskDAO.getTasksByEmployeeName(username);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle error (e.g., show an error message on the page)
            }

            request.setAttribute("tasks", tasks);
            request.getRequestDispatcher("/viewTasks.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
